//package h.jpc.vhome.parents.fragment.tools;
//
//import androidx.appcompat.app.AppCompatActivity;
//import h.jpc.vhome.R;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//public class CalculatorActivity extends AppCompatActivity {
//
//    private Button[] buttons = new Button[18];
//    private int[] ids = new int[]{R.ids.allClear,R.id.divide,R.id.multiply,R.id.clear,R.id.b0,R.id.b1,
//            R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7,R.id.b8,R.id.b9,R.id.add,R.id.subtract,
//            R.id.equal,R.id.point};
//
//    private TextView textView;
//    private String expression = "";
//    private boolean end = false;
//    private int countOperate=2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_calculator);
//
//        for (int i=0; i<ids.length;i++){
//            buttons[i] = findViewById(ids[i]);
//            buttons[i].setOnClickListener((View.OnClickListener) this);
//        }
//        textView = (TextView)findViewById(R.id.contentText);
//    }
//    public void onClick(View view) {
//        int id = view.getId();
//        Button button = (Button)view.findViewById(id);
//        String current = button.getText().toString();
//        if(end){ //如果上一次算式已经结束，则先清零
//            expression = "";
//            end = false;
//        }
//        if(current.equals("CE")){ //如果为CE则清零
//            expression = "";
//            countOperate=0;
//        }else if(current.equals("C")){ //如果点击退格
//            if(expression.length()>1){ //算式长度大于1
//                expression = expression.substring(0,expression.length()-1);//退一格
//                int i = expression.length()-1;
//                char tmp = expression.charAt(i); //获得最后一个字符
//                char tmpFront = tmp;
//                for(;i>=0;i--){ //向前搜索最近的 +-*/和.，并退出
//                    tmpFront = expression.charAt(i);
//                    if(tmpFront=='.'||tmpFront=='+'||tmpFront=='-'||tmpFront=='×'||tmpFront=='÷'){
//                        break;
//                    }
//                }
//                if(tmp>='0'&&tmp<='9'){ //最后一个字符为数字，则识别数赋值为0
//                    countOperate=0;
//                }else if(tmp==tmpFront&&tmpFront!='.') countOperate=2; //如果为+-*/，赋值为2
//                else if(tmpFront=='.') countOperate=1; //如果前面有小数点赋值为1
//            }else if(expression.length()==1){
//                expression = "";
//            }
//        }else if(current.equals(".")){
//            if(expression.equals("")||countOperate==2){
//                expression+="0"+current;
//                countOperate = 1;  //小数点按过之后赋值为1
//            }
//            if(countOperate==0){
//                expression+=".";
//                countOperate = 1;
//            }
//        }else if(current.equals("+")||current.equals("-")||current.equals("×")||current.equals("÷")){
//            if(countOperate==0){
//                expression+=current;
//                countOperate = 2;  //  +-*/按过之后赋值为2
//            }
//        }else if(current.equals("=")){ //按下=时，计算结果并显示
//            double result = (double) Math.round(count()*100)/100;
//            expression+="="+result;
//            end = true; //此次计算结束
//        }
//        else{//此处是当退格出现2+0时，用current的值替代0
//            if(expression.length()>=1){
//                char tmp1 = expression.charAt(expression.length()-1);
//                if(tmp1=='0'&&expression.length()==1){
//                    expression = expression.substring(0,expression.length()-1);
//                }
//                else if(tmp1=='0'&&expression.length()>1){
//                    char tmp2 = expression.charAt(expression.length()-2);
//                    if(tmp2=='+'||tmp2=='-'||tmp2=='×'||tmp2=='÷'){
//                        expression = expression.substring(0,expression.length()-1);
//                    }
//                }
//            }
//            expression+=current;
//            if(countOperate==2||countOperate==1) countOperate=0;
//        }
//        //    Toast.makeText(this, "countOperate:"+countOperate, Toast.LENGTH_SHORT).show();
//        textView.setText(expression); //显示出来
//    }
//    //计算逻辑，求expression表达式的值
//    private double count(){
//        double result=0;
//        double tNum=1,lowNum=0.1,num=0;
//        char tmp=0;
//        int operate = 1; //识别+-*/，为+时为正数，为-时为负数，为×时为-2/2,为/时为3/-3;
//        boolean point = false;
//        for(int i=0;i<expression.length();i++){ //遍历表达式
//            tmp = expression.charAt(i);
//            if(tmp=='.'){ //因为可能出现小数，此处进行判断是否有小数出现
//                point = true;
//                lowNum = 0.1;
//            }else if(tmp=='+'||tmp=='-'){
//                if(operate!=3&&operate!=-3){ //此处判断通用，适用于+-*
//                    tNum *= num;
//                }else{ //计算/
//                    tNum /= num;
//                }
//                //    Toast.makeText(this, "tNum = "+tNum, Toast.LENGTH_SHORT).show();
//                if(operate<0){ //累加入最终的结果
//                    result -= tNum;
//                }else{
//                    result += tNum;
//                }
//                operate = tmp=='+'?1:-1;
//                num = 0;
//                tNum = 1;
//                point = false;
//            }else if(tmp=='×'){
//                if(operate!=3&&operate!=-3){
//                    tNum *= num;
//                }else{
//                    tNum /= num;
//                }
//                operate = operate<0?-2:2;
//                point = false;
//                num = 0;
//            }else if(tmp=='÷'){
//                if(operate!=3&&operate!=-3){
//                    tNum *= num;
//                }else{
//                    tNum /= num;
//                }
//                operate = operate<0?-3:3;
//                point = false;
//                num = 0;
//            }else{
//                //读取expression中的每个数字，doube型
//                if(!point){
//                    num = num*10+tmp-'0';
//                }else{
//                    num += (tmp-'0')*lowNum;
//                    lowNum*=0.1;
//                }
//            }
//        }
//        //循环遍历结束，计算最后一个运算符后面的数
//        if(operate!=3&&operate!=-3){
//            tNum *= num;
//        }else{
//            tNum /= num;
//        }
//        //    Toast.makeText(this, "tNum = "+tNum, Toast.LENGTH_SHORT).show();
//        if(operate<0){
//            result -= tNum;
//        }else{
//            result += tNum;
//        }
//        //返回最后的结果
//        return result;
//    }
//
//
//}
