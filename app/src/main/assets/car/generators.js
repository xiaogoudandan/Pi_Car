/*
 *  Copyright 2015 Google Inc. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * @fileoverview Generators for the Turtle Blockly demo on Android.
 * @author fenichel@google.com (Rachel Fenichel)
 */
'use strict';

//移动 前进、后退、左转、右转  + 转速
Blockly.JavaScript['car_move']=function(block){
  var value = block.getFieldValue('VALUE');
  return'Car.' + block.getFieldValue('DIR') +
  '(' + value + ', \'block_id_' + block.id + '\');\n';
}

/**
 * The generated code for turtle blocks includes block ID strings.  These are useful for
 * highlighting the currently running block, but that behaviour is not supported in Android Blockly
 * as of May 2016.  This snippet generates the block code normally, then strips out the block IDs
 * for readability when displaying the code to the user.
 *
 * Post-processing the block code in this way allows us to use the same generators for the Android
 * and web versions of the turtle.
 */
Blockly.JavaScript.workspaceToCodeWithId = Blockly.JavaScript.workspaceToCode;

Blockly.JavaScript.workspaceToCode = function(workspace) {
  var code = this.workspaceToCodeWithId(workspace);
  // Strip out block IDs for readability.
  code = goog.string.trimRight(code.replace(/(,\s*)?'block_id_[^']+'\)/g, ')'))
  return code;
};
