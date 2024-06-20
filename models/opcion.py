from utils.db import db

class Opcion(db.Model):
    __tablename__ = 'opcion'
    opcionid = db.Column(db.Integer, primary_key=True)
    contenido = db.Column(db.Text)
    tipotest_id = db.Column(db.Integer, db.ForeignKey('tests.tipotest_id'), nullable=False)

    def __init__(self, opcionid, contenido, tipotest_id):
        self.opcionid = opcionid
        self.contenido = contenido
        self.tipotest_id = tipotest_id

    def serialize(self):
        return {
            'opcionid': self.opcionid,
            'contenido': self.contenido,
            'tipotest_id': self.tipotest_id,
        }
