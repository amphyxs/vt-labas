
def serialize_data(data, filename='data.yml'):
    result = None
    try:
        result = recursive_serialize(data)
    except SerializeError:
        print('Ошибка при сериализации')
        return False
    
    try:
        write_data(result, filename)  
    except Exception:
        print('Ошибка при записи данных')
        return False
    
    return True


def recursive_serialize(node, tabs=0, is_in_list=False):
    if isinstance(node, bool):
        return f'{node}'.lower()
        
    elif isinstance(node, int) or isinstance(node, float):
        return f'{node}'

    elif node is None:
        return 'null'
    
    elif isinstance(node, str):
        return f'\"{node}\"'

    elif isinstance(node, dict):
        result = ''
        is_first = True
        for key, value in node.items():
            if isinstance(value, dict) or (isinstance(value, list) and not is_short_list(value)):
                if is_first and is_in_list:
                    result += f'{key}:\n{recursive_serialize(value, tabs + 1)}\n'
                    is_first = False
                else:
                    result += f'{tab()*tabs}{key}:\n{recursive_serialize(value, tabs + 1)}\n'
            else:
                if is_first and is_in_list:
                    result += f'{key}: {recursive_serialize(value, tabs + 1)}\n'
                    is_first = False
                else:
                    result += f'{tab()*tabs}{key}: {recursive_serialize(value, tabs + 1)}\n'

        if result[-1] == '\n':
            result = result[:-1]

        return result
    
    elif isinstance(node, list):
        result = ''
        is_first = True
        is_short = is_short_list(node)
        if is_short:
            result = '['

        for index, item in enumerate(node):
            if is_short:
                result += f'{recursive_serialize(item, tabs + 1, True)}'
                result += ', ' if index != len(node) - 1 else ']'
            else:
                if is_first and is_in_list:
                    result += f'- {recursive_serialize(item, tabs + 1, True)}\n'
                    is_first = False
                else:
                    result += f'{tab()*tabs}- {recursive_serialize(item, tabs + 1, True)}\n'
        
        if result[-1] == '\n':
            result = result[:-1]

        return result
    
    else:
        raise SerializeError


def is_short_list(lst):
    is_short = True
    for item in lst:
        if not (
            isinstance(item, int) or \
            (isinstance(item, str) and len(item) <= 50)
        ):
            is_short = False
            break

    return is_short


def tab():
    return '  '


def write_data(data, filename):
    with open(filename, mode='w', encoding='utf-8') as f:
        f.write(data)


class SerializeError(Exception):
    def __init__(self, *args: object) -> None:
        super().__init__(*args)
