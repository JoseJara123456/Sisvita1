from utils.db import db

class Preguntas(db.Model):
    __tablename__ = 'preguntas'
    preguntaid = db.Column(db.Integer, primary_key=True)
    contenido = db.Column(db.Text)

    def __init__(self, preguntaid, contenido):
        self.preguntaid = preguntaid
        self.contenido = contenido
        
    def serialize(self):
        return {
            'preguntaid': self.preguntaid,
            'contenido': self.contenido,
        }