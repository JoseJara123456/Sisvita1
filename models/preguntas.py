from utils.db import db

class Preguntas(db.Model):
    __tablename__ = 'preguntas'
    preguntaid = db.Column(db.Integer, primary_key=True)
    contenido = db.Column(db.Text)
    testid = db.Column(db.Integer)

    def __init__(self, preguntaid, contenido, testid):
        self.preguntaid = preguntaid
        self.contenido = contenido
        self.testid = testid
        
    def serialize(self):
        return {
            'preguntaid': self.preguntaid,
            'contenido': self.contenido,
            'testid': self.testid,
        }