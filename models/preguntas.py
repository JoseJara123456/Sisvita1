from utils.db import db

class Preguntas(db.Model):
    __tablename__ = 'preguntas'
    preguntaid = db.Column(db.Integer, primary_key=True)
    testid = db.Column(db.Integer)
    contenido = db.Column(db.Text)

    def __init__(self, preguntaid, testid, contenido):
        self.preguntaid = preguntaid
        self.testid = testid
        self.contenido = contenido
        
    def serialize(self):
        return {
            'preguntaid': self.preguntaid,
            'testid': self.testid,
            'contenido': self.contenido,
        }