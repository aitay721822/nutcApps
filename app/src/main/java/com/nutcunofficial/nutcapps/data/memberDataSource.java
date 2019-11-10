package com.nutcunofficial.nutcapps.data;

import java.util.HashMap;

public interface memberDataSource {

    interface changePasswordCallBack{
        void onSendStatus(String response);
    }

    interface getNoticesCallBack{
        void onNoticeContent(String response);
        void onNoticeGetterFail(int StatusCode);
    }

    interface fetchUserImageCallBack{
        void onFetchSuccess(String url);
        void onFetchError(int StatusCode);
    }

    interface GetSeminarListCallBack{
        void onRequestSuccessful(String responseHTML);
        void onRequestFailure(int StatusCode);
    }

    interface GetClassmateCallback{
        void onRequestClassmateSuccessful(String html);
        void onRequestFailure(int StautsCode);
    }

    interface GetWeekTimeCallBack{
        void onRequestSuccessful(String html);
        void onRequestFailure(int StatusCode);
    }

    interface GetUserDataCallback{
        void onGettingSuccess(String html);
        void onGettingFailure(int StatusCode);
    }

    interface GetAbsentDataCallback{
        void GettingSuccess(String html);
        void GettingFailure(int StautsCode);
    }

    interface GetOptionsCallback{
        void GetOptionsData(HashMap<String,String> Options);
        void GetOptionsFail(int StatusCode);
    }

    interface checkSignInCallback{
        void SignInStatus(int StatusCode);
    }

    interface GetGradeDataCallback{
        void GetSuccess(String html);
        void GetError(int StatusCode);
    }

    void checkSignIn(checkSignInCallback callback);

    void changePassword(String oldPassword,String newPassword,final memberDataSource.changePasswordCallBack callBack);

    void getNotices(final memberDataSource.getNoticesCallBack callBack);

    void fetchUserImage(final fetchUserImageCallBack callBack);

    void GetSeminarList(memberDataSource.GetSeminarListCallBack callBack);

    void GetClassmateList(memberDataSource.GetClassmateCallback callback);

    void GetWeekClassList(memberDataSource.GetWeekTimeCallBack callback);

    void GetUserData(memberDataSource.GetUserDataCallback callBack);

    void GetAbsentData(String sem,GetAbsentDataCallback callback);

    void GetOptions(GetOptionsCallback callback);

    void GetGradeData(GetGradeDataCallback callback);

    void cancelRequest();
}
