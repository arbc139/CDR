
import random
import string

from cdr import CDR
from enum import Enum

LONG_CDR_LENGTH = 450
SHORT_CDR_LENGTH = 250
CALL_FORWARD_ADDRESS_BLANK_PROBABILITY = 0.7

def random_phone_number(mode):
  return '010{0}{1}'.format(
    str(random.randint(0, 9999)).zfill(4),
    str(random.randint(0, 9999)).zfill(4),
  )

def random_call_forward_address(mode):
  if mode == CDRSamplerMode.SHORT:
    return '<blank>'

  random_phone = random_phone_number(mode)
  if random.random() > CALL_FORWARD_ADDRESS_BLANK_PROBABILITY:
    return random_phone

  return '<blank>'

def random_ip_address(mode):
  if mode == CDRSamplerMode.SHORT:
    return '{0}.{1}.{2}.{3}'.format(
      random.randint(0, 9), random.randint(0, 9), random.randint(0, 9),
      random.randint(0, 9),
    )
  elif mode == CDRSamplerMode.LONG:
    return '{0}.{1}.{2}.{3}'.format(
      random.randint(100, 255), random.randint(100, 255),
      random.randint(100, 255), random.randint(100, 255),
    )

  return '{0}.{1}.{2}.{3}'.format(
    random.randint(0, 255), random.randint(0, 255), random.randint(0, 255),
    random.randint(0, 255),
  )

def random_string(count, contain_lower=True, contain_upper=False,
                  contain_digits=False):
  allchar = ''
  if contain_lower:
    allchar = allchar + string.ascii_lowercase
  if contain_upper:
    allchar = allchar + string.ascii_uppercase
  if contain_digits:
    allchar = allchar + string.digits
  return ''.join(random.choice(allchar) for _ in range(count))

class CDRSamplerMode(Enum):
  NORMAL = 0
  SHORT = 1
  LONG = 2
  # TODO(totorody): Check more CDR specification...
  # FREQUENT = 3
  # SECOND_FREQUENT = 4

class CDRAttributeSampler():
  def __init__(self, data_type, length, enum=None, constraint_func=None,
               custom_generator=None):
    self.data_type = data_type
    self.length = length
    self.enum = enum
    self.constraint_func = constraint_func
    self.custom_generator = custom_generator

  def make(self, mode):
    generator = None
    if self.data_type == 'int':
      generator = self.int_generator
    elif self.data_type == 'string':
      generator = self.string_generator
    else:
      generator = self.enum_generator

    if self.custom_generator:
      generator = self.custom_generator

    return generator(mode)

  def int_generator(self, mode):
    start = 0
    end = (10**self.length) - 1
    if mode == CDRSamplerMode.SHORT:
      end = 9
    elif mode == CDRSamplerMode.LONG:
      start = 10**(self.length - 1)
    return random.randint(start, end)

  def string_generator(self, mode):
    length = random.randint(1, self.length)
    if mode == CDRSamplerMode.SHORT:
      length = 1
    elif mode == CDRSamplerMode.LONG:
      length = self.length
    return random_string(
      length, contain_upper=True, contain_lower=True, contain_digits=True,
    )

  def enum_generator(self, mode):
    if mode == CDRSamplerMode.SHORT:
      min_len = min([len(str(x)) for x in self.enum])
      return random.choice([x for x in self.enum if len(str(x)) == min_len])
    elif mode == CDRSamplerMode.LONG:
      max_len = max([len(str(x)) for x in self.enum])
      return random.choice([x for x in self.enum if len(str(x)) == max_len])
    return random.choice(self.enum)

class CDRSampler():
  def __init__(self):
    # Create attributes...
    self.attributes = {
      # Length will be calculated at last...
      # 'length': CDRAttributeSampler('int', 4)
      'server': CDRAttributeSampler('enum', 2,
                             enum=[11, 12, 13, 14, 15, 16, 17, 18, 19]),
      'server_system_no': CDRAttributeSampler(
        'int', 2,
        custom_generator=lambda mode: str(random.randint(0, 99)).zfill(2),
      ),
      'service_type': CDRAttributeSampler(
        'enum', 3,
        enum=[101, 102, 103, 201, 202, 203, 205, 301, 302, 303, 304, 305, 306,
              307, 309, 310, 401, 801],
      ),
      # Assumed by totorody
      'sub_service_type': CDRAttributeSampler('enum', 5,
                                       enum=[1001, 1002, 1003, 1004, 1005]),
      'supp_service': CDRAttributeSampler(
        'enum', 2, enum=[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 90, 91],
      ),
      'accounting_record_type': CDRAttributeSampler('enum', 1, enum=[1, 5]),
      'service_ID': CDRAttributeSampler('string', 46),
      'number_of_participant': CDRAttributeSampler('int', 10),
      'calling_party_address': CDRAttributeSampler(
        'string', 16, custom_generator=random_phone_number,
      ),
      'called_party_address': CDRAttributeSampler(
        'string', 24, custom_generator=random_phone_number,
      ),
      'charging_party_address': CDRAttributeSampler(
        'string', 24, custom_generator=random_phone_number,
      ),
      'dialed_number_address': CDRAttributeSampler(
        'string', 32, custom_generator=random_phone_number,
      ),
      'call_forward_address': CDRAttributeSampler(
        'string', 24, custom_generator=random_call_forward_address,
      ),
      'role_of_node': CDRAttributeSampler('enum', 1, enum=[1, 2, 5]),
      'start_time': CDRAttributeSampler('int', 15),
      'end_time': CDRAttributeSampler('int', 15),
      'delivery_time': CDRAttributeSampler('int', 15),
      'charging_duration': CDRAttributeSampler('int', 10),
      'sip_body_size_uplink': CDRAttributeSampler('int', 10),
      'sip_body_size_downlink': CDRAttributeSampler('int', 10),
      'content_size_uplink': CDRAttributeSampler('int', 10),
      'content_size_downlink': CDRAttributeSampler('int', 10),
      'receiver_count': CDRAttributeSampler('int', 5),
      # Assumed by totorody
      'sip_code': CDRAttributeSampler(
        'enum', 3, enum=[100, 200, 300, 400, 500, 600, 700, 800, 860, 900],
      ),
      # Assumed by totorody
      'detail_code': CDRAttributeSampler(
        'enum', 4, enum=[1000, 1001, 1002, 1003, 2000, 2001, 2002],
      ),
      'cause_for_rec_closing': CDRAttributeSampler(
        'enum', 3, enum=[0, 1, 2, 3, 4, 5, 6],
      ),
      'ACR_start_lost': CDRAttributeSampler('enum', 1, enum=['T', 'F']),
      'ACR_stop_lost': CDRAttributeSampler('enum', 1, enum=['T', 'F']),
      'ACR_interim_lost': CDRAttributeSampler('enum', 1, enum=[0, 1, 2]),
      'terminal_ip_address': CDRAttributeSampler(
        'string', 15, custom_generator=random_ip_address,
      ),
      # Assumed by totorody
      'terminal_type': CDRAttributeSampler(
        'enum', 10, enum=['WIPI-1.0', 'WITOP-1.2', 'SKVM-2.0'],
      ),
      # Assumed by totorody
      'terminal_model': CDRAttributeSampler(
        'string', 16,
        custom_generator=lambda mode: '{0}-{1}'.format(
          random_string(3, contain_lower=False, contain_upper=True, contain_digits=False),
          random_string(4, contain_lower=False, contain_upper=True, contain_digits=False),
        ),
      ),
      'connection_type': CDRAttributeSampler('enum', 2, enum=['01', '02', '03', '00']),
      'pps_type': CDRAttributeSampler(
        'enum', 5,
        enum=['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '11',
              '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22',
              '23', '24', '25', '26', '27', '28', '29', '30', '31', '32', '33',
              '34', '35', '36', '37', '38', '39', '40', '41', '42', '43', '44',
              '45', '46', '47', '48', '49', '50', '51', '52', '53', '54', '55',
              '56', '57', '58', '59', '60', '61', '97', '98', '99'],
      ),
      'international_roaming': CDRAttributeSampler(
        'enum', 3, enum=['000', '001'],
      ),
      'p2p_message_count': CDRAttributeSampler('int', 5),
      'w2p_message_count': CDRAttributeSampler('int', 5),
      'w2w_message_count': CDRAttributeSampler('int', 5),
      'orig_service_domain_code': CDRAttributeSampler(
        'enum', 3,
        enum=['011', '016', '019', '774', '770', '502', '900', '901', '999'],
      ),
      'term_service_domain_code': CDRAttributeSampler(
        'enum', 3,
        enum=['011', '016', '019', '111', '901', '999', '502', '203', '204',
              '205', '206', '207', '208', '209', '210', '211', '220', '221',
              '222', '223', '224', '225', '226', '227'],
      ),
      'charging_indicator': CDRAttributeSampler('enum', 1, enum=[0, 1, 2]),
      'rat_type': CDRAttributeSampler('enum', 1, enum=[1, 2, 3]),
      # Assumed by totorody
      'cell_ID': CDRAttributeSampler(
        'string', 7,
        custom_generator=lambda mode: random_string(7,
                                                    contain_lower=True,
                                                    contain_upper=True,
                                                    contain_digits=True),
      ),
      # Assumed by totorody
      'pcs_sfi': CDRAttributeSampler(
        'string', 6,
        custom_generator=lambda mode: random_string(6,
                                                    contain_lower=True,
                                                    contain_upper=True,
                                                    contain_digits=True),
      ),
    }

  def make(self, mode=CDRSamplerMode.NORMAL):
    raw_CDR = { k: v.make(mode) for k, v in self.attributes.items() }
    return CDR(raw_CDR)
