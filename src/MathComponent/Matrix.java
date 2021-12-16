package MathComponent;

/**
 * @author LSY
 * @date 2021/12/07 19:06
 **/



public class Matrix {
    public double[] ele = {1.0, 0.0, 0.0, 0.0,
                            0.0, 1.0, 0.0, 0.0,
                            0.0, 0.0, 1.0, 0.0,
                            0.0, 0.0, 0.0, 1.0};

    public Matrix() {
    }

    public Matrix(double[] d){
        for(int i = 0; i < 16; i++){
            this.ele[i] = d[i];
        }
    }

    public Matrix(Matrix m){
        for(int i = 0; i < 16; i++){
            this.ele[i] = m.ele[i];
        }
    }

    public Matrix zero(){
        ele[0]  = 0;
        ele[5]  = 0;
        ele[10] = 0;
        ele[15] = 0;
        return this;
    }

    public Matrix identity(){

        return this;
    }

    public Matrix add(Matrix m){
        double[] temp = new double[16];
        for (int i = 0; i < 16; i++) {
            temp[i] += m.ele[i];
        }
        return new Matrix(temp);
    }

    public Matrix minus(Matrix m){
        double[] temp = new double[16];
        for (int i = 0; i < 16; i++) {
            temp[i] -= m.ele[i];
        }
        return new Matrix(temp);
    }

    public Matrix mul(Matrix m){
        double[] temp = {
                ele[0] * m.ele[0] + ele[4] * m.ele[1] + ele[8]  * m.ele[2] + ele[12] * m.ele[3],
                ele[1] * m.ele[0] + ele[5] * m.ele[1] + ele[9]  * m.ele[2] + ele[13] * m.ele[3],
                ele[2] * m.ele[0] + ele[6] * m.ele[1] + ele[10] * m.ele[2] + ele[14] * m.ele[3],
                ele[3] * m.ele[0] + ele[7] * m.ele[1] + ele[11] * m.ele[2] + ele[15] * m.ele[3],

                ele[0] * m.ele[4] + ele[4] * m.ele[5] + ele[8]  * m.ele[6] + ele[12] *  m.ele[7],
                ele[1] * m.ele[4] + ele[5] * m.ele[5] + ele[9]  * m.ele[6] + ele[13] *  m.ele[7],
                ele[2] * m.ele[4] + ele[6] * m.ele[5] + ele[10] * m.ele[6] + ele[14] *  m.ele[7],
                ele[3] * m.ele[4] + ele[7] * m.ele[5] + ele[11] * m.ele[6] + ele[15] *  m.ele[7],

                ele[0] * m.ele[8] + ele[4] * m.ele[9] + ele[8]  * m.ele[10] + ele[12] *  m.ele[11],
                ele[1] * m.ele[8] + ele[5] * m.ele[9] + ele[9]  * m.ele[10] + ele[13] *  m.ele[11],
                ele[2] * m.ele[8] + ele[6] * m.ele[9] + ele[10] * m.ele[10] + ele[14] *  m.ele[11],
                ele[3] * m.ele[8] + ele[7] * m.ele[9] + ele[11] * m.ele[10] + ele[15] *  m.ele[11],

                ele[0] * m.ele[12] + ele[4] * m.ele[13] + ele[8]  * m.ele[14] + ele[12] * m.ele[15],
                ele[1] * m.ele[12] + ele[5] * m.ele[13] + ele[9]  * m.ele[14] + ele[13] * m.ele[15],
                ele[2] * m.ele[12] + ele[6] * m.ele[13] + ele[10] * m.ele[14] + ele[14] * m.ele[15],
                ele[3] * m.ele[12] + ele[7] * m.ele[13] + ele[11] * m.ele[14] + ele[15] * m.ele[15]
        };
        return new Matrix(temp);
    }

    public Matrix rotateX(double deg){
        Matrix m = new Matrix();
        m.ele[5] = Math.cos(deg * pi180);
        m.ele[6] = Math.sin(deg * pi180);
        m.ele[9] = -Math.sin(deg * pi180);
        m.ele[10] = Math.cos(deg * pi180);
        return m;
    }

    public Matrix rotateY(double deg){
        Matrix m = new Matrix();
        m.ele[0] = Math.cos(deg * pi180);
        m.ele[2] = -Math.sin(deg * pi180);
        m.ele[8] = Math.sin(deg * pi180);
        m.ele[10] = Math.cos(deg * pi180);
        return m;
    }

    public Matrix rotateZ(double deg){
        Matrix m = new Matrix();
        m.ele[0] = Math.cos(deg * pi180);
        m.ele[1] = Math.sin(deg * pi180);
        m.ele[4] = -Math.sin(deg * pi180);
        m.ele[5] = Math.cos(deg * pi180);
        return m;
    }

    public Matrix scale(Vector4d s){
        Matrix m = new Matrix();
        m.ele[0] = s.getX();
        m.ele[5] = s.getY();
        m.ele[10] = s.getZ();
        return m;
    }

    public Matrix trans(Vector4d s){
        Matrix m = new Matrix();
        m.ele[12] = s.getX();
        m.ele[13] = s.getY();
        m.ele[14] = s.getZ();
        return m;
    }

    public double get(int i){
        return this.ele[i];
    }

    public void set(int i, double d){
        this.ele[i] = d;
    }
    //世界坐标系到观察坐标系 世界坐标系点右乘矩阵---->观察坐标系的点
    public Matrix viewTrans(Vector4d cameraP, Vector4d cameraUp, Vector4d cameraR){
        Vector4d zAxis = cameraR.crossMul(cameraUp);
        zAxis = zAxis.Normalize();
        Vector4d xAxis = cameraR;
        xAxis = xAxis.Normalize();
        Vector4d yAxis = zAxis.crossMul(xAxis);

        Matrix temp = new Matrix();

        temp.ele[0] = xAxis.getX();
        temp.ele[1] = yAxis.getX();
        temp.ele[2] = zAxis.getX();

        temp.ele[4] = xAxis.getY();
        temp.ele[5] = yAxis.getY();
        temp.ele[6] = zAxis.getY();

        temp.ele[8] =  xAxis.getZ();
        temp.ele[9] =  yAxis.getZ();
        temp.ele[10] = zAxis.getZ();

        temp.ele[12] = -xAxis.dot(cameraP);
        temp.ele[13] = -yAxis.dot(cameraP);
        temp.ele[14] = -zAxis.dot(cameraP);

        return temp;
    }

    //模型到世界变换矩阵
    public Matrix worldTransform(Vector4d pos, Vector4d scale, double xDeg, double yDeg, double zDeg){
        Matrix zm = new Matrix().rotateZ(zDeg);
        Matrix ym = new Matrix().rotateY(yDeg);
        Matrix xm = new Matrix().rotateX(xDeg);

        Matrix sc = new Matrix().scale(scale);
        Matrix tr = new Matrix().trans(pos);

        return new Matrix(xm.mul(ym).mul(zm).mul(sc).mul(tr));
    }

    //投影变换
    public Matrix projectTransform(double fovY, double aspect, double zNear, double zFar){
        Matrix temp = new Matrix().zero();
        temp.ele[0] = 1/(Math.tan(fovY * pi180) * aspect);
        temp.ele[5] = 1/ Math.tan(fovY * pi180);
        temp.ele[10] = zFar/(zFar - zNear);
        temp.ele[11] = 1.0f;
        temp.ele[14] = (zNear * zFar)/(zNear- zFar);
        return temp;
    }

    //视口变换
    public Matrix viewPointTransform(double x, double y, double width, double height, double minz, double maxz){
        Matrix temp = new Matrix();
        temp.ele[0] = width / 2;
        temp.ele[5] = -height / 2;
        temp.ele[10] = maxz - minz;
        temp.ele[12] = x + width / 2;
        temp.ele[13] = y + height / 2;
        temp.ele[14] = minz;
        return temp;
    }


    final double pi180 = 3.1415926 / 180;


}
