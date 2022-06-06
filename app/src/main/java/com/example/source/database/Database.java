package com.example.source.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.example.source.model.Account;
import com.example.source.model.Employee;
import com.example.source.model.Orders;
import com.example.source.model.OrdersDetail;
import com.example.source.model.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DoAnCuoiKy_DB.db";
    public static final int DB_VERSION = 28;
    private Context context;

    // -----------------------------Singleton Pattern--------------------------------
    static Database database;
    private Database(Context context) {
        super(context,DATABASE_NAME,null, DB_VERSION);
        this.context = context;
    }

    public static Database getInstance(Context context){
        if(database == null){
            database = new Database(context);
        }
        return database;
    }

    public static final String TABLE_NAME_ACCOUNT = "Account";
    public static final String TABLE_NAME_EMPLOYEE = "Employee";
    public static final String TABLE_NAME_PRODUCT = "Product";
    public static final String TABLE_NAME_ORDERS = "Orders";
    public static final String TABLE_NAME_ORDERS_DETAIL = "Orders_detail";


    private static final String id = "id";
    // ----------------------------------ACCOUNT----------------------------------------------
    private static final String username = "username";
    private static final String passsword = "passsword";
    private static final String fullname = "fullname";
    private static final String email = "email";
    private static final String kind = "kind";
    private static final String phone = "phone";
    private static final String avatarPath = "avatarPath";

    // ----------------------------------EMPLOYEE----------------------------------------------
    private static final String birthday = "birthday";
    private static final String accountId = "accountId";
    private static final String address = "address";
    private static final String gender = "gender";
    private static final String identityNumber = "identityNumber";
    private static final String bankNo = "bankNo";
    private static final String bankName = "bankName";
    private static final String salary = "salary";
    private static final String position = "position";

    // ----------------------------------PRODCUT----------------------------------------------
    private static final String name = "name";
    private static final String price = "price";
    private static final String saleOff = "saleOff";
    private static final String description = "description";

    // ----------------------------------ORDERS----------------------------------------------
    private static final String totalMoney = "totalMoney";
    private static final String timeOrder = "timeOrder";
    private static final String employeeId = "employeeId";
    private static final String customerName = "customerName";
    private static final String customerPhone = "customerPhone";

    // ----------------------------------ORDERS DETAIL----------------------------------------------
    private static final String productId = "productId";
    private static final String ordersId = "ordersId";
    private static final String quantity = "quantity";


    private static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE account(" +
            id + " integer primary key AUTOINCREMENT," +
            username + " NVARCHAR(100) NOT NULL," +
            passsword + " NVARCHAR(100) NOT NULL," +
            fullname + " NVARCHAR(100) NOT NULL," +
            email + " NVARCHAR(100) NOT NULL," +
            kind + " integer NOT NULL," +
            phone + " NVARCHAR(15) NOT NULL," +
            avatarPath + " BLOB)";

    private static final String CREATE_EMPLOYEE_TABLE = "CREATE TABLE employee(" +
            id + " integer primary key AUTOINCREMENT," +
            birthday + " NVARCHAR(10) NOT NULL," +
            position + " integer NOT NULL," +
            accountId + " integer NOT NULL," +
            address + " NVARCHAR(100) NOT NULL," +
            gender + " integer NOT NULL," +
            identityNumber + " NVARCHAR(100) NOT NULL," +
            bankNo + " NVARCHAR(100)," +
            bankName + " NVARCHAR(100)," +
            salary + " float NOT NULL," +
            "FOREIGN KEY (" + accountId + ") REFERENCES Account(id))";

    private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE product(" +
            id + " integer primary key AUTOINCREMENT," +
            name + " NVARCHAR(100) NOT NULL," +
            price + " float NOT NULL," +
            description + " TEXT," +
            saleOff + " Integer NOT NULL)";

    private static final String CREATE_ORDERS_TABLE = "CREATE TABLE orders(" +
            id + " integer primary key AUTOINCREMENT," +
            timeOrder + " NVARCHAR(100) NOT NULL," +
            totalMoney + " float NOT NULL," +
            customerName + " NVARCHAR(100)," +
            customerPhone + " NVARCHAR(11)," +
            employeeId + " integer NOT NULL," +
            "FOREIGN KEY (" + employeeId + ") REFERENCES Employee(id))";

    private static final String CREATE_ORDERS_DETAIL_TABLE = "CREATE TABLE orders_detail(" +
            id + " integer primary key AUTOINCREMENT," +
            quantity + " integer NOT NULL," +
            productId + " integer not null," +
            ordersId + " integer not null," +
            "FOREIGN KEY (" + productId + ") REFERENCES Product(id)," +
            "FOREIGN KEY (" + ordersId + ") REFERENCES Orders(id))";




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_EMPLOYEE_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
        db.execSQL(CREATE_ORDERS_DETAIL_TABLE);

        //admin
        db.execSQL("INSERT INTO Account(username,passsword,fullname,email,kind,phone) VALUES('admin','admin','Root Account','admin@gmail.com',1,'0934191232')");


        db.execSQL("INSERT INTO Account(username,passsword,fullname,email,kind,phone) VALUES('phongEmployee','123','La Gia Phong','phong@gmail.com',0,'09341646123')");
        db.execSQL("INSERT INTO Account(username,passsword,fullname,email,kind,phone) VALUES('baoEmployee','123','Bao NHT','bao@gmail.com',0,'09341123123')");
        db.execSQL("INSERT INTO Account(username,passsword,fullname,email,kind,phone) VALUES('quangEmployee','123','Quang TV','quang@gmail.com',0,'09123123123')");

        db.execSQL("INSERT INTO Employee(birthday,position,accountId,address,gender,identityNumber,salary) VALUES('23/06/2001',2,1,'spkt',1,'123456789',700)");
        db.execSQL("INSERT INTO Employee(birthday,position,accountId,address,gender,identityNumber,salary) VALUES('13/09/2001',1,2,'spkt',1,'121312319',500)");
        db.execSQL("INSERT INTO Employee(birthday,position,accountId,address,gender,identityNumber,salary) VALUES('03/07/2001',0,3,'spkt',1,'879876789',1)");

        db.execSQL("INSERT INTO Product(name,price,saleOff) VALUES('Cà phê sữa đá',1.5,0)");
        db.execSQL("INSERT INTO Product(name,price,saleOff) VALUES('Cà phê đá',1,0)");
        db.execSQL("INSERT INTO Product(name,price,saleOff) VALUES('Bạc sỉu',1,0)");
        db.execSQL("INSERT INTO Product(name,price,saleOff) VALUES('Nước ép táo',2,10)");
        db.execSQL("INSERT INTO Product(name,price,saleOff) VALUES('Sinh tố xoài',2,0)");

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);

        db.execSQL("INSERT INTO Orders(totalMoney,timeOrder,employeeId,customerName,customerPhone) VALUES(4,'"+ strDate +"',1,'Phong customer','090807425')");
        db.execSQL("INSERT INTO Orders(totalMoney,timeOrder,employeeId,customerName,customerPhone) VALUES(2.5,'"+ strDate +"',2,'Bao customer','091237425')");
        db.execSQL("INSERT INTO Orders(totalMoney,timeOrder,employeeId,customerName,customerPhone) VALUES(2,'"+ strDate +"',3,'Quang customer','090123123')");

        db.execSQL("INSERT INTO Orders(totalMoney,timeOrder,employeeId,customerName,customerPhone) VALUES(4,'10-4-2022 18:00:00',1,'Phong customer 2','090807425')");
        db.execSQL("INSERT INTO Orders(totalMoney,timeOrder,employeeId,customerName,customerPhone) VALUES(2.5,'15-4-2022 18:02:00',2,'Bao customer 2','091237425')");
        db.execSQL("INSERT INTO Orders(totalMoney,timeOrder,employeeId,customerName,customerPhone) VALUES(2,'08-3-2022 18:03:00',3,'Quang customer 2','090123123')");

        db.execSQL("INSERT INTO orders_detail(productId,ordersId,quantity) VALUES(2,1,1)");
        db.execSQL("INSERT INTO orders_detail(productId,ordersId,quantity) VALUES(3,1,1)");
        db.execSQL("INSERT INTO orders_detail(productId,ordersId,quantity) VALUES(4,1,1)");

        db.execSQL("INSERT INTO orders_detail(productId,ordersId,quantity) VALUES(1,2,1)");
        db.execSQL("INSERT INTO orders_detail(productId,ordersId,quantity) VALUES(3,2,1)");

        db.execSQL("INSERT INTO orders_detail(productId,ordersId,quantity) VALUES(5,3,1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME_ACCOUNT + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME_EMPLOYEE + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME_PRODUCT + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME_ORDERS + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME_ORDERS_DETAIL + "'");
        onCreate(db);
    }

    private ContentValues getValueAccount(Account account) {
        ContentValues values = new ContentValues();
        values.put(username,account.getUsername());
        values.put(passsword,account.getPasssword());
        values.put(fullname,account.getFullname());
        values.put(email,account.getEmail());
        values.put(kind,account.getKind());
        values.put(phone,account.getPhone());
        values.put(avatarPath,account.getAvatarPath());
        return values;
    }



    public Long createAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getValueAccount(account);

        Long id = db.insert(TABLE_NAME_ACCOUNT,null,values);
        db.close();
        return id;
    }

    public List<Account> searchAccount(String keyword){
        List<Account> accountList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME_ACCOUNT + " WHERE fullname LIKE '%" + keyword + "%' OR id LIKE '%" + keyword + "%' OR phone LIKE '%" + keyword + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Account account = new Account(cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getInt(5),
                        cursor.getString(6),cursor.getBlob(7));
                account.setId(cursor.getLong(0));
                accountList.add(account);
            }while (cursor.moveToNext());
        }
        db.close();
        return accountList;
    }

    public List<Account> getAllAccount(){
        List<Account> accountList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME_ACCOUNT + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Account account = new Account(cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getInt(5),
                        cursor.getString(6),cursor.getBlob(7));
                account.setId(cursor.getLong(0));
                accountList.add(account);
            }while (cursor.moveToNext());
        }
        db.close();
        return accountList;
    }

    public Account findAccountByUsername(String usernameAccount){
        Account account = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_ACCOUNT + " WHERE " + username + "= '" + usernameAccount + "'";
        Cursor cursor = db.rawQuery(query,null);
        if(!cursor.moveToFirst() || cursor.getCount() == 0){
            return null;
        }else {
            cursor.moveToFirst();
            account = new Account(cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4),cursor.getInt(5),
                    cursor.getString(6),cursor.getBlob(7));
        }
        db.close();
        return account;
    }



    public long updateAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getValueAccount(account);

        int count = db.update(TABLE_NAME_ACCOUNT,values, id + "=?"
                ,new String[]{String.valueOf(account.getId())});
        db.close();
        return count;
    }

    public long deletAccount(String idAccount){
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(TABLE_NAME_ACCOUNT,id + "=?"
                , new String[]{String.valueOf(idAccount)});
        db.close();
        return count;
    }



    //----------------------------Employee--------------------------------------

    private ContentValues getValueEmployee(Employee employee) {
        ContentValues values = new ContentValues();
        values.put(birthday,employee.getBirthday());
        values.put(accountId, employee.getAccountId());
        values.put(address, employee.getAddress());
        values.put(gender, employee.getGender());
        values.put(position, employee.getPosition());
        values.put(identityNumber, employee.getIdentityNumber());
        values.put(bankNo, employee.getBankNo());
        values.put(bankName, employee.getBankName());
        values.put(salary, employee.getSalary());
        return values;
    }


    public Long createEmployee(Employee employee){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getValueEmployee(employee);

        Long id = db.insert(TABLE_NAME_EMPLOYEE,null,values);
        db.close();
        return id;
    }

    public List<Employee> getAllEmployee(){
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME_EMPLOYEE + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Employee employee = new Employee(cursor.getString(1) ,cursor.getInt(2),
                        cursor.getLong(3),cursor.getString(4),cursor.getInt(5),
                        cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getDouble(9));
                employee.setId(cursor.getLong(0));
                employeeList.add(employee);
            }while (cursor.moveToNext());
        }
        db.close();
        return employeeList;
    }

    public Employee findEmployeeByAccountId(String idAccount){
        Employee employee = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_EMPLOYEE + " WHERE " + accountId + " = '" + idAccount + "';";
        Cursor cursor = db.rawQuery(query,null);
        if(!cursor.moveToFirst() || cursor.getCount() == 0){
            return null;
        }else {
            cursor.moveToFirst();
            employee = new Employee(cursor.getString(1),cursor.getInt(2),
                    cursor.getLong(3),cursor.getString(4),cursor.getInt(5),
                    cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getDouble(9));
            employee.setId(cursor.getLong(0));
        }
        db.close();
        return employee;
    }



    public long updateEmployee(Employee employee){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getValueEmployee(employee);

        int count = db.update(TABLE_NAME_EMPLOYEE,values, id + "=?"
                ,new String[]{String.valueOf(employee.getId())});
        db.close();
        return count;
    }

    public int deleteEmployee(String employeeId){
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(TABLE_NAME_EMPLOYEE,id + "=?"
                , new String[]{String.valueOf(employeeId)});
        db.close();
        return count;
    }





    //--------------------------------------------------Product-------------------------------------------------------
    private ContentValues getValueProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(name,product.getName());
        values.put(price, product.getPrice());
        values.put(saleOff, product.getSaleOff());
        values.put(description, product.getDescription());
        return values;
    }


    public Long createProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getValueProduct(product);

        Long id = db.insert(TABLE_NAME_PRODUCT,null,values);
        db.close();
        return id;
    }

    public Product findProductById(Long productId){
        Product product = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_PRODUCT + " WHERE " + id + "= '" + productId + "'";
        Cursor cursor = db.rawQuery(query,null);
        if(!cursor.moveToFirst() || cursor.getCount() == 0){
            return null;
        }else {
            cursor.moveToFirst();
            product = new Product(cursor.getString(1),cursor.getDouble(2),
                    cursor.getString(3),cursor.getInt(4));
            product.setId(cursor.getLong(0));
        }
        db.close();
        return product;
    }


    // ------------------------------------------------Orders----------------------------------------------------
    private ContentValues getValueOrders(Orders orders) {
        ContentValues values = new ContentValues();
        values.put(totalMoney,orders.getTotalMoney());
        values.put(timeOrder, orders.getTimeOrder());
        values.put(employeeId, orders.getEmployeeId());
        values.put(customerName, orders.getCustomerName());
        values.put(customerPhone, orders.getCustomerPhone());
        return values;
    }


    public List<Orders> searchOrders(String keyword){
        List<Orders> ordersList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME_ORDERS + " WHERE employeeId LIKE '%" + keyword + "%' OR id LIKE '%" + keyword + "%' OR customerName LIKE '%" + keyword + "%'" +
                " OR customerPhone LIKE '%" + keyword + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Orders orders = new Orders(cursor.getString(1),cursor.getDouble(2),
                        cursor.getString(3),cursor.getString(4),cursor.getLong(5));
                orders.setId(cursor.getLong(0));
                ordersList.add(orders);
            }while (cursor.moveToNext());
        }
        db.close();
        return ordersList;
    }



    public Long createOrders(Orders orders){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getValueOrders(orders);

        Long id = db.insert(TABLE_NAME_ORDERS,null,values);
        db.close();
        return id;
    }


    public List<Orders> getAllOrdersByTimeOrder(String currentTime){
        List<Orders> ordersList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME_ORDERS + " WHERE " + timeOrder + " like '" + currentTime + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Orders orders = new Orders(cursor.getString(1),cursor.getDouble(2),
                        cursor.getString(3),cursor.getString(4),cursor.getLong(5));
                orders.setId(cursor.getLong(0));
                ordersList.add(orders);
            }while (cursor.moveToNext());
        }
        db.close();
        return ordersList;
    }

    public List<Orders> getAllOrders(){
        List<Orders> ordersList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME_ORDERS + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Orders orders = new Orders(cursor.getString(1),cursor.getDouble(2),
                        cursor.getString(3),cursor.getString(4),cursor.getLong(5));
                orders.setId(cursor.getLong(0));
                ordersList.add(orders);
            }while (cursor.moveToNext());
        }
        db.close();
        return ordersList;
    }

    //----------------------------------------------Orders detail ------------------------------------------
    private ContentValues getValueOrdersDetail(OrdersDetail ordersDetail) {
        ContentValues values = new ContentValues();
        values.put(productId,ordersDetail.getProductId());
        values.put(ordersId, ordersDetail.getOrdersId());
        values.put(quantity, ordersDetail.getQuantity());
        return values;
    }


    public Long createOrdersDetail(OrdersDetail ordersDetail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getValueOrdersDetail(ordersDetail);

        Long id = db.insert(TABLE_NAME_ORDERS_DETAIL,null,values);
        db.close();
        return id;
    }

    public List<OrdersDetail> getAllOrdersDetailByOrdersId(Long idOrders){
        List<OrdersDetail> ordersDetailList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME_ORDERS_DETAIL + " WHERE " + ordersId + "= " + idOrders;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                OrdersDetail ordersDetail = new OrdersDetail(cursor.getInt(1),cursor.getLong(2),
                        cursor.getLong(3));
                ordersDetail.setId(cursor.getLong(0));
                ordersDetailList.add(ordersDetail);
            }while (cursor.moveToNext());
        }
        db.close();
        return ordersDetailList;
    }


}
