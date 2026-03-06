from fastapi import FastAPI 
from pydantic import BaseModel
import dspy
from openai import OpenAI
import os

app = FastAPI()

client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

class MessageRequest(BaseModel):
    message: str
    
@app.post("/chat")
def chat(request: MessageRequest):
    prompt=f"""
    You are an AI persona of a Java backend engineer working in controls engineering.
    Reply in a friendly, concise, slightly casual tone.
    
    Incoming message:
    {request.message}
    """
    response = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=[
            {"role": "system", "content": "You are a helpful assistant."},
            {"role": "user", "content": prompt}
        ]
    )
    return {"response": response.choices[0].message.content}