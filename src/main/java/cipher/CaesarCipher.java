package cipher;

/**
 * Шифратор и дешифратор текста на основе шифра Цезаря.
 * Приложение позволяет шифровать и дешифровать текст сдвигом по латинскому
 * алфавиту, а также выполнять дополнительные проверки и анализ.
 */

public class CaesarCipher {

    private static final int ALPHABET_SIZE = 26;

    /**
     * Функция 1: Шифрование текста шифром Цезаря.
     * Каждая латинская буква сдвигается вперёд по алфавиту на shift позиций.
     * Сдвиг автоматически нормализуется в диапазон [0, 25], поэтому можно
     * передавать отрицательные значения и значения больше 26.
     * Символы, не являющиеся латинскими буквами (пробелы, знаки препинания,
     * цифры, кириллица), переносятся в результат без изменений.
     * @param text  исходный текст (не может быть null)
     * @param shift величина сдвига (любое целое число)
     * @return зашифрованный текст
     */
    public String encrypt(String text, int shift) {
        if (text == null) {
            throw new IllegalArgumentException("Текст не может быть null");
        }
        int normalized = ((shift % ALPHABET_SIZE) + ALPHABET_SIZE) % ALPHABET_SIZE;
        StringBuilder result = new StringBuilder(text.length());
        for (char c : text.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                result.append((char) ('a' + (c - 'a' + normalized) % ALPHABET_SIZE));
            } else if (c >= 'A' && c <= 'Z') {
                result.append((char) ('A' + (c - 'A' + normalized) % ALPHABET_SIZE));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * Функция 2: Дешифрование текста, зашифрованного шифром Цезаря.
     * Выполняет обратный сдвиг. Является обратной операцией к encrypt():
     * decrypt(encrypt(text, n), n) == text.
     * @param text  зашифрованный текст (не может быть null)
     * @param shift величина сдвига, использованная при шифровании
     * @return расшифрованный текст
     */
    public String decrypt(String text, int shift) {
        if (text == null) {
            throw new IllegalArgumentException("Текст не может быть null");
        }
        return encrypt(text, -shift);
    }

    /**
     * Функция 3: ROT13 — частный случай шифра Цезаря со сдвигом 13.
     *
     * Особенность ROT13 в том, что это самодостаточная операция:
     * повторное применение возвращает исходный текст.
     *
     * @param text исходный текст (не может быть null)
     * @return преобразованный текст
     * @throws IllegalArgumentException если text == null
     */
    public String rot13(String text) {
        return encrypt(text, 13);
    }

    /**
     * Функция 4: Проверка, содержит ли текст хотя бы одну латинскую букву.
     *
     * Используется для валидации входных данных перед шифрованием:
     * если букв нет, шифрование не имеет смысла.
     *
     * @param text проверяемый текст
     * @return true, если есть хотя бы одна буква латинского алфавита
     * @throws IllegalArgumentException если text == null
     */
    public boolean containsLatinLetters(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Текст не может быть null");
        }
        for (char c : text.toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                return true;
            }
        }
        return false;
    }

    /**
     * Функция 5: Подсчёт количества латинских букв в тексте
     * @param text проверяемый текст
     * @return количество латинских букв (по факту — только строчных)
     * @throws IllegalArgumentException если text == null
     */
    public int countLetters(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Текст не может быть null");
        }
        int count = 0;
        for (char c : text.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                count++;
            }
        }
        return count;
    }
}