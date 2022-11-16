import string
import re

def parse_json_file(filename, use_regexps=False):
    """
        Parses given file and returns a python object (list or dict). 
        If there's any problems, it returns None.
    """
    text = read_data(filename)

    result = None
    try:
        if use_regexps:
            result = recursive_parse_with_regexps(text)
        else:
            result = recursive_parse(text)
    except ParseError:
        print('Ошибка при попытке парсинга')

    return result


def recursive_parse(text, parent=None):
    text = text.strip()
    if parent is None:
        if text[0] == '{':
            return recursive_parse(text[1:-1], {})
        elif text[0] == '[':
            return recursive_parse(text[1:-1], [])
        else:
            raise ParseError
    
    elif isinstance(parent, list):
        content = special_split(text, ',')
        
        for item in content:
            item = item.strip()
            if item[0] == '\"':
                parent.append(item[1:-1])
            elif item[0] in string.digits:
                parent.append(int(item))
            elif item[0] == '{':
                parent.append(recursive_parse(item[1:-1], {}))
            elif item[0] == '[':
                parent.append(recursive_parse(item[1:-1], []))
            else:
                raise ParseError
        
        return parent
    
    elif isinstance(parent, dict):
        pairs = special_split(text, ',')
        
        for pair in pairs:
            key, value = special_split(pair, ':')
            key = key.strip()[1:-1]
            value = value.strip()
            
            if value[0] == '\"':
                parent[key] = value[1:-1]
            elif value[0] in string.digits:
                parent[key] = int(value)
            elif value in ['false', 'true']:
                parent[key] = bool(value)
            elif value == 'null':
                parent[key] = None
            elif value[0] == '{':
                parent[key] = recursive_parse(value[1:-1], {})
            elif value[0] == '[':
                parent[key] = recursive_parse(value[1:-1], [])
            else:
                raise ParseError
        
        return parent
    
    else:
        raise ParseError


def recursive_parse_with_regexps(text, parent=None):
    text = text.strip()
    if parent is None:
        if re.match(r'^{', text):
            return recursive_parse_with_regexps(text[1:-1], {})
        elif re.match(r'^[', text):
            return recursive_parse_with_regexps(text[1:-1], [])
        else:
            raise ParseError
    
    elif isinstance(parent, list):
        content = special_split(text, ',')
        
        for item in content:
            item = item.strip()
            if re.match(r'^\".*\"$', item):
                parent.append(item[1:-1])
            elif re.match(r'(-?(?:0|[1-9]\d*)(?:\.\d+)?(?:[eE][+-]?\d+)?)\s*(.*)', item):
                parent.append(int(item))
            elif re.match(r'^\{', item):
                parent.append(recursive_parse_with_regexps(item[1:-1], {}))
            elif re.match(r'^\[', item):
                parent.append(recursive_parse_with_regexps(item[1:-1], []))
            else:
                raise ParseError
        
        return parent
    
    elif isinstance(parent, dict):
        pairs = special_split(text, ',')
        
        for pair in pairs:
            key, value = special_split(pair, ':')
            key = key.strip()[1:-1]
            value = value.strip()
            
            if re.match(r'^\".*\"$', value):
                parent[key] = value[1:-1]
            elif re.match(r'(-?(?:0|[1-9]\d*)(?:\.\d+)?(?:[eE][+-]?\d+)?)\s*(.*)', value):
                parent[key] = eval(value)
            elif re.match(r'^(true|false)$', value):
                parent[key] = eval(value.capitalize())
            elif re.match(r'^null$', value):
                parent[key] = None
            elif re.match(r'^\{', value):
                parent[key] = recursive_parse_with_regexps(value[1:-1], {})
            elif re.match(r'^\[', value):
                parent[key] = recursive_parse_with_regexps(value[1:-1], [])
            else:
                raise ParseError
        
        return parent
    
    else:
        raise ParseError


def find_closing_index(text, shift=0):
    bracket = text[0]
    if bracket not in '[{':
        raise ParseError
    closing = ']' if bracket == '[' else '}'
    level = 1
    for i in range(1, len(text)):
        c = text[i]
        if c in '[{':
            level += 1
        elif c in ']}':
            level -= 1
            if level == 0:
                if closing != c:
                    raise ParseError
                return i + shift
    
    return -1


def special_split(text, divider):
    result = []
    start = 0
    i = 0
    last_divider = -1
    while i < len(text):
        if text[i] == '\"':
            end = text.index('\"', i + 1)
            i = end
        elif text[i] in '{[':
            end = find_closing_index(text[i:], i)
            i = end
        elif text[i] == divider:
            result.append(text[start:i])
            last_divider = i
            start = i + 1
        i += 1
    
    if last_divider != -1:
        result.append(text[last_divider + 1:])
    else:
        result.append(text)
            
    return result     


def read_data(filename):
    try:
        f = open(filename, 'r', encoding='utf-8')
    except FileNotFoundError:
        print(f'Файла \"{filename}\" не существует')
        return None

    text = f.read().strip()
    f.close()       
    return text


class ParseError(Exception):
    def __init__(self, *args: object) -> None:
        super().__init__(*args)
