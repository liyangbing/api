/**
 *
 */
package com.domain.app.vo;

import java.io.Serializable;

/**
 * 项目名称：forum
 *
 * @author Wind-spg
 * @version V1.0.0
 * @description: 前台基础vo对象
 * @create_time：2014年10月8日 下午4:08:44
 */
public class BaseResultVo implements Serializable {

    /**
     * {变量说明}
     */
    private static final long serialVersionUID = -7978635757653362784L;

    // private static final Log LOGGER = LogFactory.getLog(BaseResultVo.class);

    // 返回码，200表示成功，非200表示失败
    private int resultCode;

    // 返回消息，成功为“success”，失败为具体失败信息
    private String resultMessage;

    // 返回数据
    private Object resultData;

    public BaseResultVo() {

    }

    public BaseResultVo(String resultMessage, Object resultData) {
        this.resultMessage = resultMessage;
        this.resultData = resultData;
    }

    public BaseResultVo(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    @Override
    public String toString() {
        return "BaseResultVo [resultCode=" + resultCode + ", resultMessage=" + resultMessage
                + ", resultData=" + resultData + "]";
    }

}
