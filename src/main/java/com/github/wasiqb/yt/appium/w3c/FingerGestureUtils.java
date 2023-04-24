package com.github.wasiqb.yt.appium.w3c;

import static com.github.wasiqb.yt.appium.w3c.FingerGestureUtils.Direction.LEFT;
import static com.github.wasiqb.yt.appium.w3c.FingerGestureUtils.Direction.RIGHT;
import static java.text.MessageFormat.format;
import static java.time.Duration.ZERO;
import static java.time.Duration.ofMillis;
import static java.util.Collections.singletonList;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;

import java.util.Arrays;

import io.appium.java_client.AppiumDriver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

@AllArgsConstructor
public class FingerGestureUtils<D extends AppiumDriver> {
    @Getter
    @AllArgsConstructor
    public enum Direction {
        LEFT (-1, 0),
        RIGHT (1, 0),
        UP (0, -1),
        DOWN (0, 1);

        private final int x;
        private final int y;
    }

    private static final String FINGER_1 = "Finger 1";
    private static final String FINGER_2 = "Finger 2";

    private final D driver;

    public void dragTo (final WebElement source, final WebElement target) {
        final var start = getElementCenter (source);
        final var end = getElementCenter (target);

        System.out.println ("Drag Drop...");
        printPoint ("Start", start);
        printPoint ("End", end);

        final var sequence = singleFingerSwipe (FINGER_1, 0, start, end);

        this.driver.perform (singletonList (sequence));
    }

    public void swipe (final Direction direction, final int distance) {
        swipe (direction, null, distance);
    }

    public void swipe (final Direction direction, final WebElement element, final int distance) {
        final var start = getSwipeStartPosition (element);
        final var end = getSwipeEndPosition (direction, element, distance);

        System.out.println ("Swipe...");
        if (element != null) {
            printDimension ("Element Size", element.getSize ());
            printPoint ("Element location", element.getLocation ());
        }
        printPoint ("Start", start);
        printPoint ("End", end);

        final var sequence = singleFingerSwipe (FINGER_1, 0, start, end);

        this.driver.perform (singletonList (sequence));
    }

    public void tap (final WebElement element) {
        final var start = getElementCenter (element);
        final var sequence = singleFingerSwipe (FINGER_1, 0, start, null);

        this.driver.perform (singletonList (sequence));
    }

    public void zoomIn (final WebElement element, final int distance) {
        System.out.println ("Zoom In...");
        printDimension ("Screen Size", getScreenSize ());
        printDimension ("Element Size", element.getSize ());
        printPoint ("Element location", element.getLocation ());

        final var start = getSwipeStartPosition (element);
        final var start1 = new Point (start.getX () - 50, start.getY ());
        final var start2 = new Point (start.getX () + 50, start.getY ());

        final var end1 = getSwipeEndPosition (LEFT, element, distance);
        final var end2 = getSwipeEndPosition (RIGHT, element, distance);

        printPoint ("Start 1", start1);
        printPoint ("Start 2", start2);
        printPoint ("End 1", end1);
        printPoint ("End 2", end2);

        final var sequence1 = singleFingerSwipe (FINGER_1, 0, start1, end1);
        final var sequence2 = singleFingerSwipe (FINGER_2, 1, start2, end2);

        this.driver.perform (Arrays.asList (sequence1, sequence2));
    }

    public void zoomOut (final WebElement element, final int distance) {
        System.out.println ("Zoom Out...");
        printDimension ("Screen Size", getScreenSize ());
        printDimension ("Element Size", element.getSize ());
        printPoint ("Element location", element.getLocation ());

        final var start = getSwipeStartPosition (element);
        final var start1 = new Point (start.getX () - 50, start.getY ());
        final var start2 = new Point (start.getX () + 50, start.getY ());

        final var end1 = getSwipeEndPosition (LEFT, element, distance);
        final var end2 = getSwipeEndPosition (RIGHT, element, distance);

        printPoint ("Start 1", start1);
        printPoint ("Start 2", start2);
        printPoint ("End 1", end1);
        printPoint ("End 2", end2);

        final var sequence1 = singleFingerSwipe (FINGER_1, 0, end1, start1);
        final var sequence2 = singleFingerSwipe (FINGER_2, 1, end2, start2);

        this.driver.perform (Arrays.asList (sequence1, sequence2));
    }

    private Point getCorrectedCoordinates (final WebElement element, final Point point) {
        final var screenSize = getScreenSize ();
        var x = point.getX ();
        var y = point.getY ();
        var w = screenSize.getWidth ();
        var h = screenSize.getHeight ();

        if (element != null) {
            final var size = element.getSize ();
            final var location = element.getLocation ();
            w = size.getWidth () + location.getX ();
            h = size.getHeight () + location.getY ();
        }

        if (x >= w) {
            x = w - 5;
        }
        if (y >= h) {
            y = h - 5;
        }
        if (x < 0) {
            x = 5;
        }
        if (y < 0) {
            y = 5;
        }

        return new Point (x, y);
    }

    private Point getElementCenter (final WebElement element) {
        final var location = element.getLocation ();
        final var size = element.getSize ();
        final var x = (size.getWidth () / 2) + location.getX ();
        final var y = (size.getHeight () / 2) + location.getY ();
        return getCorrectedCoordinates (element, new Point (x, y));
    }

    private Dimension getScreenSize () {
        return this.driver.manage ()
            .window ()
            .getSize ();
    }

    private Point getSwipeEndPosition (final Direction direction, final WebElement element, final int distance) {
        final var start = getSwipeStartPosition (element);
        final var x = start.getX () + ((start.getX () * direction.getX () * distance) / 100);
        final var y = start.getY () + ((start.getY () * direction.getY () * distance) / 100);
        return getCorrectedCoordinates (element, new Point (x, y));
    }

    private Point getSwipeStartPosition (final WebElement element) {
        final var screenSize = getScreenSize ();
        var x = screenSize.getWidth () / 2;
        var y = screenSize.getHeight () / 2;
        if (element != null) {
            final var point = getElementCenter (element);
            x = point.getX ();
            y = point.getY ();
        }
        return new Point (x, y);
    }

    private void printDimension (final String type, final Dimension dimension) {
        System.out.println (format ("{0}: [w: {1}, h: {2}]", type, dimension.getWidth (), dimension.getHeight ()));
    }

    private void printPoint (final String type, final Point point) {
        System.out.println (format ("{0}: [x: {1}, y: {2}]", type, point.getX (), point.getY ()));
    }

    private Sequence singleFingerSwipe (final String fingerName, final int index, final Point start, final Point end) {
        final var finger = new PointerInput (TOUCH, fingerName);
        final var sequence = new Sequence (finger, index);

        sequence.addAction (finger.createPointerMove (ZERO, viewport (), start.getX (), start.getY ()));
        sequence.addAction (finger.createPointerDown (PointerInput.MouseButton.LEFT.asArg ()));

        if (end != null) {
            sequence.addAction (new Pause (finger, ofMillis (500)));
            sequence.addAction (finger.createPointerMove (ofMillis (500), viewport (), end.getX (), end.getY ()));
        }

        sequence.addAction (finger.createPointerUp (PointerInput.MouseButton.LEFT.asArg ()));

        return sequence;
    }
}
