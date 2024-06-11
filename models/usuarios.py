from utils.db import db

class Usuarios(db.Model):
    __tablename__ = 'usuarios'
    usuarioid = db.Column(db.Integer, primary_key=True)
    nombre = db.Column(db.String(100))
    email = db.Column(db.String(100))
    password = db.Column(db.String(200))
    rol = db.Column(db.String(20))

    def __init__(self, usuarioid, nombre, email, password, rol):
        self.usuarioid = usuarioid
        self.nombre = nombre
        self.email = email
        self.password = password
        self.rol = rol
        
    def serialize(self):
        return {
            'usuarioid': self.UsuarioID,
            'nombre': self.nombre,
            'email': self.email,
            'password': self.password,
            'rol': self.rol,
        }
