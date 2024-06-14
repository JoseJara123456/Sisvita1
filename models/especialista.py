from utils.db import db

class Especialista(db.Model):
    __tablename__ = 'especialista'
    usuarioid = db.Column(db.Integer, primary_key=True)
    nombre = db.Column(db.String(100))
    email = db.Column(db.String(100))
    password = db.Column(db.String(200))
    rol = db.Column(db.String(20))
    especialidad = db.Column(db.String(100))

    def __init__(self, nombre, email, password, rol, especialidad):
        self.nombre = nombre
        self.email = email
        self.password = password
        self.rol = rol
        self.especialidad = especialidad
        
    def serialize(self):
        return {
            'usuarioid': self.UsuarioID,
            'nombre': self.nombre,
            'email': self.email,
            'password': self.password,
            'rol': self.rol,
            'especialidad': self.especialidad
        }