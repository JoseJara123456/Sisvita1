
class especialistas():

    def __init__(self, usuarioid, especialidad) -> None:
        self.usuarioid= usuarioid
        self.especialidad = especialidad

    def to_JSON(self):
        return {
            'usuarioid': self.usuarioid,
            'especialidad': self.especialidad,
        }