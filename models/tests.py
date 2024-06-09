from utils.db import db

class Tests(db.Model):
    __tablename__ = 'tests'
    testid = db.Column(db.Integer, primary_key=True)
    titulo = db.Column(db.String(255))
    descripcion = db.Column(db.Text)
    pregunta_id = db.Column(db.Integer)
    estudiante_id = db.Column(db.Integer)
    especialista_id = db.Column(db.Integer)
    fecha_asignacion = db.Column(db.DateTime)

    def __init__(self, testid, titulo, descripcion, estudiante_id, especialista_id, fecha_asignacion):
        self.testid = testid
        self.titulo = titulo
        self.descripcion = descripcion
        self.estudiante_id = estudiante_id
        self.especialista_id = especialista_id
        self.fecha_asignacion = fecha_asignacion
        
    def serialize(self):
        return {
            'testid': self.testid,
            'titulo': self.titulo,
            'descripcion': self.descripcion,
            'estudiante_id': self.estudiante_id,
            'especialista_id': self.especialista_id,
            'fecha_asignacion': self.fecha_asignacion.isoformat() if self.fecha_asignacion else None,
        }