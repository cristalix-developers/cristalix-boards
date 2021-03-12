# Cristalix boards

## Установка

Проект cristalix-boards необходимо включать в один из своих плагинов.

```groovy
repositories {
    maven {
        url 'https://repo.implario.dev/public'
    }
}

dependencies {
    implementation 'ru.cristalix:boards-bukkit-api:3.0.0'
}
```

## Использование

Управление топами осуществляется через интерфейс `Board`

Сперва нужно создать, настроить и добавить таблицу в мир.

```java
// Создание топа
Board board = Boards.newBoard();

// Настройка имён колонок и их ширины
board.addColumn("#", 10);
board.addColumn("Игрок", 80);
board.addColumn("Побед", 30);
board.addColumn("Игр", 30);

// Заголовок топа
board.setTitle("Топ по победам");

// Местоположение (включая поворот)
board.setLocation(location);

// Добавление в мир после того, как всё настроено
Boards.addBoard(board);
```

Заметка: Если вы не создаёте топы до входа первых игроков, рекомендуется
вызвать метод `Boards.init()`

После того, как таблица создана, её можно наполнять контентом:

```java

// Перед добавлением нового контента, стоит очистить доску от существующего
board.clearContent();

board.addContent(UUID.randomUUID(), "§e1", "DelfikPro", "1000", "1500");
board.addContent(UUID.randomUUID(), "§e2", "kaso", "1000", "1500");
board.addContent(UUID.randomUUID(), "§e3", "CaptainLach", "1000", "1500");
board.addContent(UUID.randomUUID(), "§e4", "ilyafx", "1000", "1500");
board.addContent(UUID.randomUUID(), "§e5", "xxDark", "1000", "1500");
board.addContent(UUID.randomUUID(), "§e6", "HelloWorld", "1000", "1500");
board.addContent(UUID.randomUUID(), "§e7", "AppleJuice", "1000", "1500");

// Отправить обновление всем игрокам
board.updateContent();
```

Параметр UUID будет использоваться, чтобы перемещать ячейки с анимацией, а также для рендера скинов.  
В данный момент он не используется.
