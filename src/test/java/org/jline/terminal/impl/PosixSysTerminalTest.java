/*
 * Copyright (c) 2002-2016, the original author or authors.
 *
 * This software is distributable under the BSD license. See the terms of the
 * BSD license in the documentation provided with this software.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package org.jline.terminal.impl;

import org.easymock.EasyMock;
import org.jline.terminal.Attributes;
import org.jline.terminal.Terminal.Signal;
import org.jline.terminal.Terminal.SignalHandler;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class PosixSysTerminalTest {

    @Test
    public void testNativeSignalsDefault() throws Exception {
        Pty pty = EasyMock.createNiceMock(Pty.class);
        EasyMock.expect(pty.getAttr()).andReturn(new Attributes()).anyTimes();
        EasyMock.expect(pty.getSlaveInput()).andReturn(new ByteArrayInputStream(new byte[0])).anyTimes();
        EasyMock.expect(pty.getSlaveOutput()).andReturn(new ByteArrayOutputStream()).anyTimes();
        EasyMock.replay(pty);
        try (PosixSysTerminal terminal = new PosixSysTerminal(
                "name", "ansi", pty,
                Charset.defaultCharset().name(), true,
                SignalHandler.SIG_DFL)) {
            assertEquals(0, terminal.nativeHandlers.size());
        }
    }

    @Test
    public void testNativeSignalsIgnore() throws Exception {
        Pty pty = EasyMock.createNiceMock(Pty.class);
        EasyMock.expect(pty.getAttr()).andReturn(new Attributes()).anyTimes();
        EasyMock.expect(pty.getSlaveInput()).andReturn(new ByteArrayInputStream(new byte[0])).anyTimes();
        EasyMock.expect(pty.getSlaveOutput()).andReturn(new ByteArrayOutputStream()).anyTimes();
        EasyMock.replay(pty);
        try (PosixSysTerminal terminal = new PosixSysTerminal(
                "name", "ansi", pty,
                Charset.defaultCharset().name(), true,
                SignalHandler.SIG_IGN)) {
            assertEquals(0, terminal.nativeHandlers.size());
        }
    }

    @Test
    public void testNativeSignalsRegister() throws Exception {
        Pty pty = EasyMock.createNiceMock(Pty.class);
        EasyMock.expect(pty.getAttr()).andReturn(new Attributes()).anyTimes();
        EasyMock.expect(pty.getSlaveInput()).andReturn(new ByteArrayInputStream(new byte[0])).anyTimes();
        EasyMock.expect(pty.getSlaveOutput()).andReturn(new ByteArrayOutputStream()).anyTimes();
        EasyMock.replay(pty);
        try (PosixSysTerminal terminal = new PosixSysTerminal(
                "name", "ansi", pty,
                Charset.defaultCharset().name(), true,
                SignalHandler.SIG_DFL)) {
            assertEquals(0, terminal.nativeHandlers.size());
            SignalHandler prev = terminal.handle(Signal.INT, s -> {});
            assertEquals(1, terminal.nativeHandlers.size());
            terminal.handle(Signal.INT, prev);
            assertEquals(0, terminal.nativeHandlers.size());
        }
    }
}