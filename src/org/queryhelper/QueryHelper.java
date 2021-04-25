package org.queryhelper;

import java.util.StringTokenizer;
import java.util.regex.PatternSyntaxException;

/** @author Soe Min Htut */
public class QueryHelper {

  private String msg;
  private String topText, leftText, rightText;

  public QueryHelper(String topText, String leftText, String rightText) {
    this.topText   = topText;
    this.leftText  = leftText;
    this.rightText = rightText;
    this.msg = "";
  }

  private int collectFormulaTokenCount() {
    int formulaTokenCount = -1;
    if (isItOkToExecute()) {
      try {
        if (topText != null && topText.contains("#")) {
          formulaTokenCount = topText.split("#").length - 1;

        } else {
          msg += "Error: # is required on the top formula text.\n";
          formulaTokenCount = -1;
        }
      } catch (PatternSyntaxException pse) {
        msg += "Error: " + pse.getMessage() + " \n";
        formulaTokenCount = -1;
      }
    }
    return formulaTokenCount;
  }

  private void putDataIntoExpression(int formulaTokenCount) {
    int inputTokenCount = 0;
    String eachLineText;

    if (isItOkToExecute()) {

      StringTokenizer enter = new StringTokenizer(leftText, "\n");
      rightText = "";

      while (enter.hasMoreTokens()) {

        eachLineText = String.valueOf(topText);

        String singleLineData = enter.nextToken().trim();
        StringTokenizer eachToken = new StringTokenizer(singleLineData, ";");

        while (eachToken.hasMoreTokens()
            && eachLineText.contains("#")
            && inputTokenCount < formulaTokenCount) {
          eachLineText = eachLineText.replaceFirst("#", eachToken.nextToken().trim());
          inputTokenCount++;
        }

        rightText += eachLineText + "\n";
      }
      msg = "OK";

    } else {
      rightText = null;
    }
  }

  private boolean isItOkToExecute() {
    if (isTopTextEmpty()) {
      msg += "Error: the top formula text is required.\n";
    }
    if (!isTopTextContainSharp()) {
      msg += "Error: the top formula texts must contain '#' symbol.\n";
    }
    if (isLeftTextEmpty()) {
      msg += "Error: the left input data text is required.\n";
    }
    if (!isLeftTextContainSemiColon()) {
      msg += "Error: the left input data must contain ';' symbol.\n";
    }
    return (!isTopTextEmpty()
        && isTopTextContainSharp()
        && !isLeftTextEmpty()
        && isLeftTextContainSemiColon());
  }

  private boolean isTopTextEmpty() {
    return (topText == null || topText.trim().isEmpty()) ? true : false;
  }

  private boolean isTopTextContainSharp() {
    return (topText != null && topText.contains("#")) ? true : false;
  }

  private boolean isLeftTextEmpty() {
    return (leftText == null || leftText.trim().isEmpty()) ? true : false;
  }

  private boolean isLeftTextContainSemiColon() {
    return (leftText != null && leftText.contains(";")) ? true : false;
  }

  public void execute() {
    putDataIntoExpression(collectFormulaTokenCount());
  }

  public String getTopText() {
    return topText;
  }

  public void setTopText(String topText) {
    this.topText = topText;
  }

  public String getLeftText() {
    return leftText;
  }

  public void setLeftText(String leftText) {
    this.leftText = leftText;
  }

  public String getRightText() {
    return rightText;
  }

  public void setRightText(String rightText) {
    this.rightText = rightText;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
