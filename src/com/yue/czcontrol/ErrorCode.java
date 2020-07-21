package com.yue.czcontrol;

/**
 <h1> Please use first number and digits to decide the Error Code From. </h1>


 <h1> System Error: (3 digits)</h1>
 <p> 101 - Incompatible Version Error. </p>
 <p> 102 - Socket Connect Failed Error. </p>
 <p> 103 - DataBase Connect Failed Error. </p>
 <p> 104 - DataBase Object Close Error. </p>

 <h1> System Exception: (3 digits)</h1>
 <p> 201 - IOException. </p>
 <p> 202 - UploadFailed. </p>

 <h1> Exception: (4 digits)</h1>

 <h2> 1. LoginController. </h2>
 <p> 1001 - LoginFailed. </p>
 <p> 1002 - PinIsWrong. </p>

 <h2> 2. SignUpController </h2>
 <p> 2001 - PasswordIsDifferent. </p>
 <p> 2002 - FormatNotMatch. </p>
 <p> 2003 - DataNotCompleted. </p>
 <p> 2004 - AccountIsExist. </p>

 <h2> 3. EditMemberController </h2>
 <p> 3001 - NameNotFound. </p>
 <p> 3002 - NotExistMember. </p>

 <h2> 4. AddLeaveController </h2>
 <p> 4001 - IDNotExist. </p>
 <p> 4002 - LeaveDataNotCompleted </p>

 <h2> 5. DeleteMemberController </h2>
 <p> 5001 - HandlerIsWrong </p>
 <p> 5002 - DeleteIDNotFound </p>

 <h2> 6. AddMemberController </h2>
 <p> 6001 - AddDataNotCompleted </p>

 <h1> 10001 = Unknown Exception </h1>

 <p> Code By Yue. </p>

 */
public enum ErrorCode{

    /**
     * Incompatible Version, Error Code: 101.
     */
    IncompatibleVersions(101),
    /**
     * Socket Connect Failed, Error Code: 102.
     */
    SocketConnectFailed(102),
    /**
     * DataBase Connect Failed, Error Code: 103.
     */
    DBConnectFailed(103),
    /**
     * DataBase Object Close Failed, Error Code: 104.
     */
    DBCloseFailed(104),

    /**
     * IOException, Error Code: 201.
     */
    IO(201),
    /**
     * UploadFailedException, Error Code: 202.
     */
    UploadFailed(202),

    /**
     * Login Failed, Error Code: 1001.
     */
    LoginFailed(1001),
    /**
     * Pin input wrong, Error Code: 1002.
     */
    PinIsWrong(1002),

    /**
     * Password Is Different, Error Code: 2001.
     */
    PasswordIsDifferent(2001),
    /**
     * Format Not Match, Error Code: 2002.
     */
    FormatNotMatch(2002),
    /**
     * Register Data Not Completed, Error Code: 2003.
     */
    DataNotCompleted(2003),
    /**
     * Account Is Exist, Error Code: 2004.
     */
    AccountIsExist(2004),

    /**
     * Name Not Found, Error Code: 3001.
     */
    NameNotFound(3001),
    /**
     * Not Exist Member, Error Code: 3002.
     */
    NotExistMember(3002),

    /**
     * ID not exist, Error Code: 4001.
     */
    IDNotExist(4001),
    /**
     * Leave Data not completed, Error Code: 4002.
     */
    LeaveDataNotCompleted(4002),

    /**
     * The user is not equals member handler, Error Code: 5001.
     */
    HandlerIsWrong(5001),
    /**
     * Member id is not found, Error Code: 5002.
     */
    DeleteIDNotFound(5002),

    /**
     * Add Data not completed, Error Code: 6001.
     */
    AddDataNotCompleted(6001),

    /**
     * Unknown Exception, Error Code: 10001.
     */
    Unknown(10001);

    /**
     * Error Code.
     */
    private final int code;

    /**
     * Error code constructor.
     * @param code Error Code
     */
    ErrorCode(final int code) {
        this.code = code;
    }

    /**
     * Get error code.
     * @return this.code
     */
    public int getCode() {
        return this.code;
    }
}
