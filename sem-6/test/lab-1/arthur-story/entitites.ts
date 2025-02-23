export class Character {
  constructor(
    public name: string,
    public action: string[],
    public state: string | null = null
  ) {}
}

export class Objekt {
  constructor(public name: string, public state: string | null = null) {}
}

export class Event {
  constructor(public action: string, public reaction: string) {}
}

export class Panel extends Objekt {
  constructor(panelName: string) {
    super(`панель ${panelName}`, null);
  }

  setText(text: string) {
    this.state = `На панели загорелись слова: ${text}`;
  }
}

export class Button extends Objekt {
  constructor(buttonName: string, public readonly parentPanel: Panel) {
    super(`${buttonName} кнопка`, null);
  }

  press(character: Character) {
    this.parentPanel.setText('Пожалуйста, не трогайте больше эту кнопку');
    character.state = 'вздрогнул';
  }
}
