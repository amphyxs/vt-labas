import { arthur, ford, panel, redButton } from './story';

describe('Existing Objects', () => {
  describe('arthur', () => {
    test('should have the correct name', () => {
      expect(arthur.name).toBe('Артур');
    });

    test('should have the correct actions', () => {
      expect(arthur.action).toEqual([
        'слушал',
        'глядел по сторонам',
        'нажал кнопку',
      ]);
    });

    test('should have a null state initially', () => {
      expect(arthur.state).toBeNull();
    });
  });

  describe('ford', () => {
    test('should have the correct name', () => {
      expect(ford.name).toBe('Форд');
    });

    test('should have the correct actions', () => {
      expect(ford.action).toEqual(['говорил']);
    });

    test('should have a null state initially', () => {
      expect(ford.state).toBeNull();
    });
  });

  describe('panel', () => {
    test('should have the correct name', () => {
      expect(panel.name).toBe('панель компьютерного банка');
    });

    test('should have a null state initially', () => {
      expect(panel.state).toBeNull();
    });

    test('should set text correctly', () => {
      panel.setText('Предупреждение');
      expect(panel.state).toBe('На панели загорелись слова: Предупреждение');
    });
  });

  describe('redButton', () => {
    test('should have the correct name', () => {
      expect(redButton.name).toBe('красная кнопка');
    });

    test('should have the correct parent panel', () => {
      expect(redButton.parentPanel).toBe(panel);
    });

    test('should update panel and character state when pressed', () => {
      redButton.press(arthur);
      expect(panel.state).toBe(
        'На панели загорелись слова: Пожалуйста, не трогайте больше эту кнопку'
      );
      expect(arthur.state).toBe('вздрогнул');
    });
  });
});
