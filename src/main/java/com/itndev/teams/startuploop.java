package com.itndev.teams;

import com.itndev.teams.ItemUtils.ItemFixer;

public class startuploop {

    @Deprecated
    public static void StartUpLoopsTEMP() {
        System.out.println("STARTING UP LOOP TASKS (RUNNING ASYNC FOR PERFORMANCE)");
        //utils.tagliveupdater();
        ItemFixer.ItemFixTask();
    }
}
