from utils.db import db

class Opciones(db.Model):
    __tablename__ = 'opciones'
    opcionid = db.Column(db.Integer, primary_key=True)
    contenido = db.Column(db.Text)
    preguntaid = db.Column(db.Integer, db.ForeignKey('preguntas.preguntaid'), nullable=False)

    def __init__(self, opcionid, contenido, preguntaid):
        self.opcionid = opcionid
        self.contenido = contenido
        self.preguntaid = preguntaid

    def serialize(self):
        return {
            'opcionid': self.opcionid,
            'contenido': self.contenido,
            'preguntaid': self.preguntaid,
        }
