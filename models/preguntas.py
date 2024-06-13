from utils.db import db

class Preguntas(db.Model):
    __tablename__ = 'preguntas'
    preguntaid = db.Column(db.Integer, primary_key=True)
    contenido = db.Column(db.Text)
    tipotest_id = db.Column(db.Integer)

    def __init__(self, preguntaid, contenido, tipotest_id):
        self.preguntaid = preguntaid
        self.contenido = contenido
        self.tipotest_id = tipotest_id
        
    def serialize(self):
        return {
            'preguntaid': self.preguntaid,
            'contenido': self.contenido,
            'tipotest_id': self.tipotest_id,
        }
    
    