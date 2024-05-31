from utils.db import db

class Estudiantes(db.Model):
    __tablename__ = 'estudiantes'
    usuarioid = db.Column(db.Integer, primary_key=True)

    def __init__(self, usuarioid):
        self.usuarioid = usuarioid
        
    def serialize(self):
        return {
            'usuarioid': self.UsuarioID
        }