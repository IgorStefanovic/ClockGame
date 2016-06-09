LOCAL_PATH := $(call my-dir)


include $(CLEAR_VARS)
LOCAL_MODULE := game
LOCAL_SRC_FILES := game.c
LOCAL_STATIC_LIBRARIES := libgametime
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libgametime
LOCAL_SRC_FILES := libgametime.a
include $(PREBUILT_STATIC_LIBRARY)
