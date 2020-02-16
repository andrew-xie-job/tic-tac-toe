package com.andrew.solutions.tictactoe.security;

import com.andrew.solutions.tictactoe.domain.Player;
import com.google.common.collect.ImmutableSet;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ContextUser extends User {
    private final Player player;

    public ContextUser(Player player) {
        super(player.getUserName(), player.getPassword(), ImmutableSet.of(new SimpleGrantedAuthority("create")));
        this.player = player;
    }

    public Player getPlayer() {
        return  player;
    }
}
