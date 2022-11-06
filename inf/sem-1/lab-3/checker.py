import sys

use_tests = (len(sys.argv) >= 2) and (sys.argv[1] == '-t')
first_read = True


def read_input(task_id):
    global use_tests, first_read
    inp_cnt = 1
    while True:
        if not first_read and not use_tests:
            exit(0)

        if not use_tests:
            print('⌨️  Введите текст и по окончании ввода нажмите Ctrl+D.')
            
        try:
            inp = open(f'tests/task{task_id}/in{inp_cnt}', mode='r', encoding='utf-8') \
                if use_tests \
                else sys.stdin
            first_read = False
        except FileNotFoundError:
            return
        
        input_str = inp.read().strip()

        yield input_str, inp_cnt
        inp_cnt += 1


def check_output(output, task_id, inp_cnt):
    if not use_tests:
        return True
    
    correct_output = open(f'tests/task{task_id}/out{inp_cnt}', mode='r', encoding='utf-8').read().strip()
    if str(output).strip() != correct_output:
        print(f'❌ Тест {inp_cnt} провален (правильный вывод:\"{correct_output}\")')
    else:
        print(f'✅ Тест {inp_cnt} пройден')
