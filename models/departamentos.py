from utils.db import db

class Departamento(db.Model):
    __tablename__ = 'departamentos'
    departamentoid = db.Column(db.String(2), primary_key=True)
    nombre = db.Column(db.String(100), nullable=False)

    def __init__(self, departamentoid, nombre):
        self.departamentoid = departamentoid
        self.nombre = nombre

    def serialize(self):
        return {
            'departamentoid': self.departamentoid,
            'nombre': self.nombre
        }