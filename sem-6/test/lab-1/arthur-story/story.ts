import { Button, Character, Panel } from './entitites';

export const arthur = new Character('Артур', [
  'слушал',
  'глядел по сторонам',
  'нажал кнопку',
]);
export const ford = new Character('Форд', ['говорил']);

export const panel = new Panel('компьютерного банка');
export const redButton = new Button('красная', panel);
