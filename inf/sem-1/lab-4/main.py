from json_parser import parse_json_file, read_data
from yaml_serializer import serialize_data, write_data
import json
import yaml
import timeit
import schedule_pb2


def main_task():
    """Конвертировать schedule.json в schedule.yml"""
    data = parse_json_file('schedule.json')
    serialize_data(data, filename='schedule.yml')


def extra_task_1():
    """Конвертировать schedule.json в schedule_with_lib.yml с использованием библиотек"""
    text = read_data('schedule.json')
    data = json.loads(text)
    serialized = yaml.dump(data, allow_unicode=True)
    write_data(serialized, filename='schedule_with_lib.yml')    
    

def extra_task_2():
    """Конвертировать schedule.json в schedule_with_regexps.yml с использованием regexps"""
    data = parse_json_file('schedule.json', use_regexps=True)
    serialize_data(data, filename='schedule_with_regexps.yml')


def extra_task_3():
    """Сравнить стократное время выполнения парсинга + конвертации в цикле для трёх методов"""
    print(f'{"Обязательное":^12}: {timeit.timeit("main_task()", "from __main__ import main_task", number=100)} сек.')
    print(f'{"1-е доп.":^12}: {timeit.timeit("extra_task_1()", "from __main__ import extra_task_1", number=100)} сек.')
    print(f'{"2-е доп.":^12}: {timeit.timeit("extra_task_2()", "from __main__ import extra_task_2", number=100)} сек.')
    

def extra_task_4():
    """Ковертировать schedule.json в protocol buffer"""
    # Сериализация
    data = parse_json_file('schedule.json')
    schedule_proto = schedule_pb2.Schedule()
    schedule_proto.day = data['day']
    for subject in data['subjects']:
        subject_proto = schedule_proto.subjects.add()
        for key, value in subject.items():
            if isinstance(value, list):
                getattr(subject_proto, key).extend(value)
            else:
                setattr(subject_proto, key, value)
            
    serialized_proto = schedule_proto.SerializeToString()
    with open('schedule_protobuf', 'wb') as f:
        f.write(serialized_proto)
    
    # Чтение
    with open('schedule_protobuf', 'rb') as f:
        data = f.read()
    
    new_schedule_proto = schedule_pb2.Schedule()
    new_schedule_proto.ParseFromString(data)
    print(f'День: {new_schedule_proto.day}')
    for subject_proto in new_schedule_proto.subjects:
        print(f'Предмет: {subject_proto.subjectName}')
        print(f'Время начала: {subject_proto.beginTime}')
        print(f'Время окончания: {subject_proto.beginTime}')
        print(f'Время окончания: {subject_proto.endTime}')
        print(f'Номера недель: {subject_proto.weekNumbers}')
        print(f'Аудитория: {subject_proto.auditorium}')
        print(f'Место проведения: {subject_proto.place}')
        print(f'Чётная неделя: {"да" if subject_proto.isEvenWeek else "нет"}')
        print(f'Чётная неделя: {"да" if subject_proto.isEvenWeek else "нет"}')
        print(f'Преподаватель: {subject_proto.teacher}')
        print(f'Формат проведения: {subject_proto.format}')
        print('-'*50)

    
if __name__ == '__main__':
    main_task()
    extra_task_1()
    extra_task_2()
    extra_task_3()
    extra_task_4()