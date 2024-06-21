from utils.db import db

class Distrito(db.Model):
    __tablename__ = 'distritos'
    distritoid = db.Column(db.String(6), primary_key=True)
    nombre = db.Column(db.String(100), nullable=False)
    provincia_id = db.Column(db.String(4), db.ForeignKey('provincias.provinciaid'), nullable=False)
    
    def __init__(self, distritoid, nombre, provincia_id):
        self.distritoid = distritoid
        self.nombre = nombre
        self.provincia_id = provincia_id

    def serialize(self):
        return {
            'distritoid': self.distritoid,
            'nombre': self.nombre,
            'provincia_id': self.provincia_id
        }
