from utils.db import db

class Especialistas(db.Model):
    __tablename__ = 'especialistas'
    usuarioid = db.Column(db.Integer, primary_key=True)
    especialidad = db.Column(db.String(100))

    def __init__(self, usuarioid, especialidad):
        self.usuarioid = usuarioid
        self.especialidad = especialidad
        
    def serialize(self):
        return {
            'usuarioid': self.usuarioid,
            'especialidad': self.especialidad,
        }
