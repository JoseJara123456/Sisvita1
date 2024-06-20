from utils.db import db

class testRealizados(db.Model):
    __tablename__ = 'test_realizados'
    test_id = db.Column(db.Integer, primary_key=True)
    fecha_test = db.Column(db.DateTime)
    tipotest_id = db.Column(db.Integer)
    estudiante_id = db.Column(db.Integer)
    puntaje = db.Column(db.Integer)

    def __init__(self, fecha_test, tipotest_id, estudiante_id, puntaje):
        self.fecha_test = fecha_test
        self.tipotest_id = tipotest_id
        self.estudiante_id = estudiante_id
        self.puntaje = puntaje

    def serialize(self):
        return {
            'test_id': self.test_id,
            'fecha_test': self.fecha_test.strftime('%Y-%m-%d') if self.fecha_test else None,
            'tipotest_id': self.tipotest_id,
            'estudiante_id': self.estudiante_id,
            'puntaje': self.puntaje
        }
