package net.fellbaum.jemoji;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class EmojiManagerTest {

    public static final String ALL_EMOJIS_STRING = EmojiManager.getAllEmojisLengthDescending().stream().map(Emoji::getEmoji).collect(Collectors.joining());
    private static final String SIMPLE_EMOJI_STRING = "Hello ❤️ ❤ ❤❤️ World";

    @Test
    public void extractEmojisInOrder() {
        List<Emoji> emojis = EmojiManager.extractEmojisInOrder(ALL_EMOJIS_STRING + ALL_EMOJIS_STRING);

        assertEquals(EmojiManager.getAllEmojisLengthDescending().size() * 2, emojis.size());

        List<Emoji> allEmojis = new ArrayList<>(EmojiManager.getAllEmojisLengthDescending());
        allEmojis.addAll(EmojiManager.getAllEmojisLengthDescending());
        assertEquals(allEmojis, emojis);
    }

    @Test
    public void extractEmojis() {
        Set<Emoji> emojis = EmojiManager.extractEmojis(ALL_EMOJIS_STRING + ALL_EMOJIS_STRING);

        assertEquals(EmojiManager.getAllEmojisLengthDescending().size(), emojis.size());
        Set<Emoji> allEmojis = EmojiManager.getAllEmojis();
        assertEquals(allEmojis, emojis);
    }

    @Test
    public void getEmoji() {
        String emojiString = "👍";

        Optional<Emoji> emoji = EmojiManager.getEmoji(emojiString);
        assertTrue(emoji.isPresent());
        assertEquals(emojiString, emoji.orElseThrow(RuntimeException::new).getEmoji());
    }

    @Test
    public void isEmoji() {
        String emojiString = "\uD83D\uDC4D";

        assertTrue(EmojiManager.isEmoji(emojiString));
    }

    @Test
    public void getByAlias() {
        String alias = "smile";

        Optional<Emoji> emoji = EmojiManager.getByAlias(alias);
        assertTrue(emoji.isPresent());
        assertEquals("😄", emoji.orElseThrow(RuntimeException::new).getEmoji());
    }

    @Test
    public void getByAliasWithColon() {
        String alias = ":smile:";

        Optional<Emoji> emoji = EmojiManager.getByAlias(alias);
        assertTrue(emoji.isPresent());
        assertEquals("😄", emoji.orElseThrow(RuntimeException::new).getEmoji());
    }

    @Test
    public void containsEmoji() {
        assertTrue(EmojiManager.containsEmoji(SIMPLE_EMOJI_STRING));
    }

    @Test
    public void removeEmojis() {
        assertEquals("Hello    World", EmojiManager.removeAllEmojis(SIMPLE_EMOJI_STRING));
    }

    @Test
    public void removeAllEmojisExcept() {
        assertEquals("Hello ❤️  ❤️ World", EmojiManager.removeAllEmojisExcept(SIMPLE_EMOJI_STRING + "👍", EmojiManager.getEmoji("❤️").orElseThrow(RuntimeException::new)));
    }

    @Test
    public void replaceEmojis() {
        assertEquals("Hello :heart: ❤ ❤:heart: World", EmojiManager.replaceEmojis(SIMPLE_EMOJI_STRING, ":heart:", EmojiManager.getEmoji("❤️").orElseThrow(RuntimeException::new)));
    }

    @Test
    public void replaceOnlyUnqualifiedEmoji() {
        assertEquals("Hello ❤️ :heart: :heart:❤️ World", EmojiManager.replaceEmojis(SIMPLE_EMOJI_STRING, ":heart:", EmojiManager.getEmoji("❤").orElseThrow(RuntimeException::new)));
    }

    @Test
    public void replaceAllEmojis() {
        assertEquals("Hello something something somethingsomething World something something something", EmojiManager.replaceAllEmojis(SIMPLE_EMOJI_STRING + " 👍 👨🏿‍🦱 😊", "something"));
    }

    @Test
    public void replaceAllEmojisFunction() {
        assertEquals("Hello SMILEYS_AND_EMOTION SMILEYS_AND_EMOTION SMILEYS_AND_EMOTIONSMILEYS_AND_EMOTION World PEOPLE_AND_BODY PEOPLE_AND_BODY SMILEYS_AND_EMOTION", EmojiManager.replaceAllEmojis(SIMPLE_EMOJI_STRING + " 👍 👨🏿‍🦱 😊", emoji -> emoji.getGroup().toString()));
    }

}