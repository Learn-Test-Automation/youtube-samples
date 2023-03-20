package com.github.wasiqb.yt.appium;

import static java.time.Duration.ZERO;
import static java.util.Collections.singletonList;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;

import java.time.Duration;

import io.appium.java_client.AppiumDriver;
import lombok.AllArgsConstructor;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

@AllArgsConstructor
public class FingerGestureUtils<D extends AppiumDriver> {
    private final D driver;

    public void swipeUp () {
        final var start = getSwipeStartPosition ();
        final var end = getSwipeEndPosition ();

        final var finger = new PointerInput (TOUCH, "Finger1");
        final var sequence = new Sequence (finger, 0);

        sequence.addAction (finger.createPointerMove (ZERO, viewport (), start.getX (), start.getY ()));
        sequence.addAction (finger.createPointerDown (LEFT.asArg ()));
        sequence.addAction (finger.createPointerMove (Duration.ofMillis (300), viewport (), end.getX (), end.getY ()));
        sequence.addAction (finger.createPointerUp (LEFT.asArg ()));

        this.driver.perform (singletonList (sequence));
    }

    private Point getSwipeEndPosition () {
        final var start = getSwipeStartPosition ();
        final var y = start.getY () + ((start.getY () * -1 * 50) / 100);
        return new Point (start.getX (), y);
    }

    private Point getSwipeStartPosition () {
        final var screenSize = this.driver.manage ()
            .window ()
            .getSize ();
        final var x = screenSize.getWidth () / 2;
        final var y = screenSize.getHeight () / 2;
        return new Point (x, y);
    }
}
