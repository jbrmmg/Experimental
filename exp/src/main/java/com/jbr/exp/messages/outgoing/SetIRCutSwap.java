package com.jbr.exp.messages.outgoing;

import com.fasterxml.jackson.annotation.JsonGetter;

public class SetIRCutSwap extends BaseOutgoingMessage {
    public class IRCutSwap {
        private final Integer IRCutSwap;

        public IRCutSwap(Integer IRCutSwap) {
            this.IRCutSwap = IRCutSwap;
        }

        @JsonGetter("IrcutSwap")
        public Integer getIRCutSwap() {
            return IRCutSwap;
        }
    }

    private final IRCutSwap data;

    public SetIRCutSwap(String sessionId, Integer irCutSwap) {
        super((short)1040,"Camera.Param.[0]",sessionId);

        this.data = new IRCutSwap(irCutSwap);
    }

    @JsonGetter("Camera.Param.[0]")
    public IRCutSwap getData() {
        return data;
    }
}
