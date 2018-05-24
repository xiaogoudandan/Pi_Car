'use strict';

//移动 前进、后退、左转、右转  + 转速
Blockly.Python['car_move']=function(block){
  var value = block.getFieldValue('VALUE');
  return 'Car.' + block.getFieldValue('DIR') +
  '(' + value+ ')\n';
}


Blockly.Python['left_right_avoidance']=function(block){
  var code = 'Car.' + block.getFieldValue('DIR') + '()';
  return [code, Blockly.Python.ORDER_NONE];
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
  var b=Blockly.Python.valueToCode(block,"VALUE",Blockly.Python.ORDER_NONE)||"\"\"";
  var name = block.getFieldValue('DIR');
  if(name == "yes"){
    return 'Car.print' + '(' + b+ ',' + '\"\\n\"' + ')\n';
  }else{
    return 'Car.print' + '(' + b+ ','+ "\"\"" +')\n';
  }
}

