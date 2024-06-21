from utils.db import db

class Estudiante(db.Model):
    __tablename__ = 'estudiante'
    usuarioid = db.Column(db.Integer, primary_key=True)
    nombre = db.Column(db.String(100), nullable=False)
    email = db.Column(db.String(100), nullable=False, unique=True)
    password = db.Column(db.String(200), nullable=False)
    rol = db.Column(db.String(20), nullable=False)
    departamento_id = db.Column(db.String(2), db.ForeignKey('departamentos.departamentoid'))
    provincia_id = db.Column(db.String(4), db.ForeignKey('provincias.provinciaid'))
    distrito_id = db.Column(db.String(6), db.ForeignKey('distritos.distritoid'))



    def __init__(self, nombre, email, password, rol,departamento_id,provincia_id,distrito_id):
        self.nombre = nombre
        self.email = email
        self.password = password
        self.rol = rol
        self.departamento_id = departamento_id
        self.provincia_id = provincia_id
        self.distrito_id = distrito_id
        
    def serialize(self):
        return {
            'usuarioid': self.usuarioid,
            'nombre': self.nombre,
            'email': self.email,
            'password': self.password,
            'rol': self.rol,
            'departamento_id': self.departamento_id,
            'provincia_id': self.provincia_id,
            'distrito_id': self.distrito_id
        }
