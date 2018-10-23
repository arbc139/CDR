
import pickle
from functools import reduce

class CDR():
  @classmethod
  def deserialize(cls, serialized_data):
    return cls(pickle.loads(serialized_data))

  def deserialize_from_file(cls, file):
    return cls(pickle.load(file))

  def __init__(self, raw_CDR):
    self.dict_CDR = raw_CDR
    self.dict_CDR['length'] = reduce(
      lambda total, x : len(str(x)) + total,
      self.dict_CDR.values(),
      0,
    )

  def __str__(self):
    return str(self.dict_CDR)

  def __hash__(self):
    return int(self.dict_CDR)

  def serialize(self):
    return pickle.dumps(self.dict_CDR)

  def serialize_to_file(self, file):
    pickle.dump(self.dict_CDR, file)
