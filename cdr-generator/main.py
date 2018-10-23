
import sys
import random

from cdr import CDR
from enum import Enum
from sampler import CDRSampler, CDRSamplerMode
from utils import parse_commands

class DataType(Enum):
  FIRST_FREQUENT = 0
  SECOND_FREQUENT = 1
  SHORT = 2
  LONG = 3

# Generate CDR(Call Data Record) data samples.
def main():
  command_configs = {
    '-c': {
      'longInputForm': '--count',
      'field': 'count',
    },
    '-f': {
      'longInputForm': '--frequent-cdr-ratio',
      'field': 'frequent_cdr_ratio',
    },
    '-l': {
      'longInputForm': '--long-cdr-ratio',
      'field': 'long_cdr_ratio',
    },
    '-s': {
      'longInputForm': '--short-cdr-ratio',
      'field': 'short_cdr_ratio',
    },
    '-e': {
      'longInputForm': '--error-ratio',
      'field': 'error_ratio',
    },
    '-o': {
      'longInputForm': '--output',
      'field': 'output',
    },
  }
  commands = parse_commands(sys.argv[1:], command_configs)
  count = int(commands.count)
  first_frequent_cdr_ratio = float(commands.frequent_cdr_ratio)
  long_cdr_ratio = float(commands.long_cdr_ratio)
  short_cdr_ratio = float(commands.long_cdr_ratio)
  second_frequent_cdr_ratio = 1 - first_frequent_cdr_ratio \
                                - long_cdr_ratio - short_cdr_ratio
  error_ratio = float(commands.error_ratio)

  sampler = CDRSampler()
  serialized_cdrs = []
  print('# Job Start')
  with open(commands.output, 'wb') as f:
    for i in range(count):
      if i % 10000 == 0:
        print('{0} iteration'.format(i))
      pick = random.choices(
        [ DataType.FIRST_FREQUENT, DataType.SECOND_FREQUENT, DataType.SHORT,
          DataType.LONG ],
        [ first_frequent_cdr_ratio, second_frequent_cdr_ratio, short_cdr_ratio,
          long_cdr_ratio ],
      )
      if pick == DataType.SHORT:
        sampler.make(CDRSamplerMode.SHORT).serialize_to_file(f)
      elif pick == DataType.LONG:
        sampler.make(CDRSamplerMode.LONG).serialize_to_file(f)
      else:
        # TODO(totorody): Diverse frequent cdr ratio to first and second.
        sampler.make().serialize_to_file(f)
  print('# Job Finish')

if __name__ == '__main__':
  main()
