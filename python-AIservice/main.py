from fastapi import FastAPI
from pydantic import BaseModel
import dspy
import os

# Configure LLM
lm = dspy.LM("openai/gpt-3.5-turbo", api_key=os.getenv("OPENAI_API_KEY"))
dspy.configure(lm=lm)

app = FastAPI()

class MessageRequest(BaseModel):
    message: str


# Define the Persona
class HumanPersona(dspy.Signature):
    """Reply like a friendly Java backend engineer working in Goldman Sachs in the controls engineering team. He likes Coffee and Coding."""
    
    message = dspy.InputField()
    response = dspy.OutputField()


persona_ai = dspy.Predict(HumanPersona)

#uvicron main:app --reload --port 8000
@app.post("/chat")
def chat(request: MessageRequest):
    result = persona_ai(message=request.message)
    return {"response": result.response}
