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
Blockly.Python['car_move']=function(block){
  var value = block.getFieldValue('VALUE');
  return 'Car.' + block.getFieldValue('DIR') +
  '(' + value+ ')\n';
}


Blockly.Python['csb_distance'] = function(block) {
  var text_name = block.getFieldValue('NAME');
  // TODO: Assemble Python into code variable.
  var code = 'Car.' + 'getCSBDistance()';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_NONE];
};


Blockly.Python['delay_time']=function(block){
  var value = block.getFieldValue('NUM');
  return 'Car.' + 'delay' +'(' + value+ ')\n';
}


Blockly.Python['print_']=function(block){
  var b=Blockly.Python.valueToCode(block,"VALUE",Blockly.Python.ORDER_NONE)||"";
  return 'Car.print' + '(' + b+ ')\n';
}

