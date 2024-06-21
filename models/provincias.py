from utils.db import db

class Provincia(db.Model):
    __tablename__ = 'provincias'
    provinciaid = db.Column(db.String(4), primary_key=True)
    nombre = db.Column(db.String(100), nullable=False)
    departamento_id = db.Column(db.String(2), db.ForeignKey('departamentos.departamentoid'), nullable=False)

    def __init__(self, provinciaid, nombre, departamento_id):
        self.provinciaid = provinciaid
        self.nombre = nombre
        self.departamento_id = departamento_id

    def serialize(self):
        return {
            'provinciaid': self.provinciaid,
            'nombre': self.nombre,
            'departamento_id': self.departamento_id
        }
