#include "game.h"
#include "gametime.h"
#include <jni.h>

JNIEXPORT jlong JNICALL Java_rtrk_pnrs_clockgame_Game_incrementTime
  (JNIEnv *env, jclass jc, jlong time, jlong add) {
      return (jlong) incrementTime(time, add);
  }
  
  JNIEXPORT jlong JNICALL Java_rtrk_pnrs_clockgame_Game_decrementTime
    (JNIEnv *env, jclass jc, jlong time, jlong add) {
        return (jlong) decrementTime(time, add);
    }
