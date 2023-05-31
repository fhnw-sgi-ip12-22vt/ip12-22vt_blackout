package com.blackout.model;

public enum SyncState {
  WantsToPlayAlone,
  WaitsForOtherUserToConfirmVS,
  WaitsForOtherUserToAnswerQuestion,
  WaitsForOtherUserToConfirmResult,
  Undefined
}
