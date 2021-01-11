package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Constant.Constant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class VMTScreen implements Screen {

  private Stage render;
  private TextureRegion board;
  private Image boardbotIMG;
  private TMan tman;
  private Group groupboard;
  private int row = 8;
  private int col = 8;
  private float offsetX = 28.5f, offsetY = 28.5f, unit = 80;

  private Image img;

  private List<int[][]> listArr = new ArrayList<>();
  private List<Group> groupListTocreate;
  private int[][] listAll = new int[row][col];
  private Image[][] listAllBlock = new Image[row][col];
  private Image bgimg;




  public VMTScreen()
  {
    tman = Main.getAssset();
    render = Main.getRender();
    listArr.add(Constant.arrO);
    listArr.add(Constant.arrII);
    listArr.add(Constant.arrO1);
    listArr.add(Constant.arrL);
    listArr.add(Constant.arr_);
    listArr.add(Constant.arrE_);
    listArr.add(Constant.arro);
    listArr.add(Constant.arr3_);
    listArr.add(Constant.arrLnguoc);
    groupListTocreate =  new ArrayList<>();
  }

  @Override
  public void show() {
    board = tman.getTG("board");
    bgimg = new Image(tman.getTG("bg"));
    render.addActor(bgimg);
    bgimg.setX((render.getWidth()-bgimg.getWidth())/2);
    createBoard();
    CreateRadomGroup();
    createAllBlock();

  }

   private void createBoard()
  {
    Image boardIMG = new Image(board);
    boardbotIMG = new Image(tman.getTG("bottom"));
    groupboard = new Group();
    groupboard.setSize(697, 697);
    groupboard.addActor(boardIMG);
    groupboard.setPosition((render.getWidth() - groupboard.getWidth())/2,400);
    groupboard.setDebug(true);


    //int n = row*col;
    /*for (int i = 0; i < n; i+=2) {
        Image blk = new Image(Main.getAssset().getTG("block"));
        blk.setPosition(offsetX + 80*(i%col) + ((i/col)%2 == 0 ? 0 : 80), offsetY + 80*(i/row));
        groupboard.addActor(blk);
    }
    */

    for (int i = 0; i <row;i++){
      for(int j =0; j <col;j++){
          if((i + j + 1) %2 == 0){
            Image blk = new Image(Main.getAssset().getTG("block"));
            blk.setPosition(offsetX + unit*i , offsetY + unit*j);
            groupboard.addActor(blk);
          }
          else{
            Image blk = new Image(Main.getAssset().getTG("block1"));
            blk.setPosition(offsetX + unit*i , offsetY + unit*j);
            groupboard.addActor(blk);
          }
      }
    }
    render.addActor(groupboard);
    render.addActor(boardbotIMG);
    boardbotIMG.setPosition((render.getWidth()-groupboard.getWidth())/2,120);
  }

  private void createAllBlock(){
    for (int i = 0; i <listAllBlock.length;i++){
      for(int j =0; j <listAllBlock.length;j++){

          Image blk = new Image(Main.getAssset().getTG("gem0_p1"));
          listAllBlock[i][j] = blk;
          blk.setPosition(offsetX + unit*j , offsetY + unit*i);
          groupboard.addActor(blk);
          blk.getColor().a = 0;
          //blk.setDebug(true);
      }
    }
  }

  private void CreateRadomGroup()
    {
        for (int i =0; i< 3; i++) {
            int ran = new Random().nextInt(listArr.size());
            create(ran, i);
        }
    }

  private  int countRow(int arr) {
    int count =0;

    for (int i=0;i<listArr.get(arr).length;i++){
      if(listArr.get(arr)[i][0] == 1)
        count++;
    }
    return count;
  }

  private  int countCol(int arr) {

    int max =0;
    for (int j =0; j < listArr.get(arr).length;j++){
      for (int i=0; i<listArr.get(arr)[0].length;i++){
        if(listArr.get(arr)[j][i] == 1 && i > max)
          max = i;
      }
    }

    return max + 1;
  }

  private void create(int arr,int ipos)
  {
    Group groupBlock = new Group();

    for(int i = 0;i < listArr.get(arr).length; i++){
      for(int j =0 ;j < listArr.get(arr)[0].length; j++){
        if(listArr.get(arr)[i][j] == 1)
        {
          img = new Image(tman.getTG("gem0_p1"));

          render.addActor(img);
          groupBlock.addActor(img);
          img.setOrigin(Align.center);
          img.setX(img.getWidth() * j);
          img.setY(img.getHeight()* -i);

          groupBlock.setTransform(true);
          groupBlock.setSize(img.getWidth() * countCol(arr),img.getHeight() * countRow(arr)) ;
        }
      }
    }

    render.addActor(groupBlock);
    groupListTocreate.add(groupBlock);
    groupBlock.setScale(0.5f);
    groupBlock.setOrigin(Align.left);
    groupBlock.setX(((boardbotIMG.getWidth()/3) * ipos) + 100);
    groupBlock.setY(bgimg.getY(Align.bottom) + (boardbotIMG.getHeight()/2));
    groupBlock.setDebug(true);

    groupBlock.addListener(new DragListener(){
      float px = 0, py = 0;
      float ox = 0, oy = 0;
      float posX = groupBlock.getX(), posY = groupBlock.getY();
      @Override
      public void drag(InputEvent event, float x, float y, int pointer) {

        groupBlock.setPosition(groupBlock.getX() + x - px,groupBlock.getY() + y - py);

        if(groupBlock.getX() < groupboard.getX() || groupBlock.getY() < groupboard.getY()
                                                 || groupBlock.getX() + groupBlock.getWidth() > groupboard.getX(Align.right)
                                                 || groupBlock.getY() + groupBlock.getHeight() > groupboard.getY(Align.top)
                                                 || isOverlap(groupBlock)){
          RemoveAlpha();
        }
        else {
          CreateAlpha(groupBlock);
        }

      }
      @Override
      public void dragStart(InputEvent event, float x, float y, int pointer) {
        groupBlock.setScale(1f);
        px = x;
        py = y;

        super.dragStart(event, x, y, pointer);
      }
      @Override
      public void dragStop(InputEvent event, float x, float y, int pointer) {
        ox = groupBlock.getX();
        oy = groupBlock.getY();

        if(ox < groupboard.getX() || oy < groupboard.getY()
                || ox + groupBlock.getWidth() > groupboard.getX(Align.right)
                || oy +groupBlock.getHeight() > groupboard.getY(Align.top) || isOverlap(groupBlock)){
          RemoveAlpha();
          groupBlock.addAction(parallel(moveTo(posX,posY,0.08f),scaleTo(0.5f,0.5f)));
        }
        else{

          PutDown(groupBlock,arr);
          groupBlock.clearListeners();
          groupListTocreate.remove(groupBlock);
          if(groupListTocreate.size() == 0){
            CreateRadomGroup();
          }
          //////// check full row /////
          CheckFull();
        }
        super.dragStop(event, x, y, pointer);
      }
    });


        for (int i = 0;i<groupBlock.getChildren().size;i++){
          int  row = listArr.get(arr).length;
          int  col = listArr.get(arr)[0].length;
           if(col ==  3 && row == 3){
              groupBlock.getChild(i).setY(groupBlock.getChild(i).getY() + (img.getHeight()*2));
              groupBlock.setSize(img.getWidth() * 3,img.getHeight()* 3);
            }
            if(row == 2 && col ==2){
              groupBlock.getChild(i).setY(groupBlock.getChild(i).getY() + (img.getHeight()));
              groupBlock.setY(boardbotIMG.getHeight()/2 + groupBlock.getHeight()/4);
            }
            if(row == 1 && col ==4){
              groupBlock.setY(boardbotIMG.getHeight()/2 + groupBlock.getHeight());
              groupBlock.setX(groupBlock.getX() - 10f);
            }
            if(row == 3 && col == 2){
              groupBlock.getChild(i).setY(groupBlock.getChild(i).getY() + (img.getHeight()*2));
              groupBlock.setY(boardbotIMG.getHeight()/2);
              groupBlock.setSize(img.getWidth() * 2,img.getHeight()* 3);
            }
            if(row == 1 && col ==1){
              groupBlock.setY(boardbotIMG.getHeight() - groupBlock.getHeight()/3);
            }


        }

        //groupBlock.setY(100f);
  }

  private  boolean isOverlap(Group gr)
  {
    float padSx =  groupboard.getX();//11.8f;
    //float padBx = 28.5f;
    int baseCol  = (int)((gr.getX() - padSx)/unit);
    float padSy = groupboard.getY();
    //float padBy = 28.5f;
    int baseRow = (int)((gr.getY() - padSy)/unit);
    int i=0;

    boolean isoverlap = false;
      while (i < gr.getChildren().size){
        int childCol = (int) (gr.getChildren().get(i).getX() / unit) + baseCol;
        int childRow = (int) (gr.getChildren().get(i).getY() / unit) + baseRow;
        if(listAll[childRow][childCol] == 1)
          isoverlap =  true;
        i++;
      }
    return  isoverlap;
  }

  private  void RemoveAlpha() {

    for(int i=0; i< listAll.length ; i++){
      for (int j = 0; j < listAll.length ; j++){
        listAllBlock[i][j].setScale(1f);
        if(listAll[i][j] == 0){
          listAllBlock[i][j].getColor().a = 0;
        }
        else{
          listAllBlock[i][j].getColor().a = 1;
        }
      }
    }
  }

  private void PutDown(Group gr,int arr)
  {
    float padSx =  groupboard.getX();//11.8f;
    //float padBx = 28.5f;
    int baseCol  = (int)((gr.getX() - padSx)/unit);

    float padSy = groupboard.getY();
    //float padBy = 28.5f;
    int baseRow = (int)((gr.getY() - padSy)/unit);

    for (int i = 0;i < gr.getChildren().size;i++){
      int childCol = (int)((gr.getChildren().get(i).getX())/unit) + baseCol;
      int childRow = (int)((gr.getChildren().get(i).getY())/unit) + baseRow;
      listAll[childRow][childCol] = 1;
    }

    clearScale();

    for (int i =0;i<gr.getChildren().size;i++) {
      int childCol = (int) ((gr.getChildren().get(i).getX()) / unit) + baseCol;
      int childRow = (int) ((gr.getChildren().get(i).getY()) / unit) + baseRow;
      listAllBlock[childRow][childCol].getColor().a = 1f;
      gr.remove();
    }

  }
  private void clearScale()
  {
    for (int i = 0; i<listAllBlock.length;i++){
      for (int j = 0; j < listAllBlock.length;j++){
        listAllBlock[i][j].setScale(1f);
        //listAllBlock[i][j].setColor(Color.WHITE);
      }
    }
  }
  private void CreateAlpha(Group gr) {

    if(gr.getX() >= groupboard.getX() && gr.getY() >= groupboard.getY() && gr.getX() + gr.getWidth() < groupboard.getX(Align.right) &&
                                            gr.getY() + gr.getHeight() < groupboard.getY(Align.top)){

    float padSx =  groupboard.getX();
    int baseCol  = (int)((gr.getX() - padSx )/unit);
    float padSy = groupboard.getY();
    int baseRow = (int)((gr.getY() - padSy )/unit);

      RemoveAlpha();

      int[][] arrClone = new int[listAll.length][];
      for (int i = 0; i < listAll.length; ++i) {
        // allocating space for each row of destination array
        arrClone[i] = new int[listAll[i].length];
        System.arraycopy(listAll[i], 0, arrClone[i], 0, arrClone[i].length);
      }

      for (int i =0;i<gr.getChildren().size;i++){
          int col = (int) ((gr.getChildren().get(i).getX()) / unit);
          int row = (int) ((gr.getChildren().get(i).getY()) / unit);
          int childCol = col + baseCol;
          int childRow = row + baseRow;

          if(listAll[childRow][childCol] == 0){
            listAllBlock[childRow][childCol].getColor().a = 0.5f;
            arrClone[childRow][childCol] = 1;
          }
          else{
            listAllBlock[childRow][childCol].getColor().a = 1;
          }
      }

//      for (int i = 0; i < arrClone.length; i++) { //this equals to the row in our matrix.
//      for (int j = 0; j < arrClone[i].length; j++) { //this equals to the column in each row.
//        System.out.print(arrClone[i][j] + "");
//      }
//      System.out.println("******************"); //change line on console as row comes to end in the matrix.
//    }
      CheckDestroy(arrClone);
    }
  }
  private void CheckDestroy(int[][] arr){

    for (int i=0; i<row; i++){
      CheckFullrowDes(i,arr);
    }
    for (int i=0; i<col; i++){
      CheckFullcolDes(i,arr);
    }
  }
  private void CheckFullrowDes(int Row, int[][] arr){
    int dem=0;
    for (int j=0;j<col;j++){
      if(arr[Row][j]==1){
        dem++;
      }
    }
    if(dem == col){
      for (int j=0;j<col;j++){
        if(arr[Row][j]==1){
          listAllBlock[Row][j].setScale(1.1f);
        }
      }
    }
  }
  private void CheckFullcolDes(int column, int[][] arr){

    int dem=0;
    for (int j= 0 ; j<row ; j++){
      if(arr[j][column]==1){
        dem++;
      }
    }
    if(dem==row)
      for (int j=0;j<row;j++){
        if(arr[j][column]==1){
          listAllBlock[j][column].setScale(1.1f);

        }
      }
  }

  @Override
  public void render(float delta) {
    render.act(delta);
    render.draw();
  }

  @Override
  public void resize(int width, int height) {
        render.getViewport().update(width,height,false);
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {

  }

  private void CheckFull(){
    List<Integer> listRow = new ArrayList<>();
    List<Integer> listCol = new ArrayList<>();

    for (int i=0; i<row; i++){
      if(CheckFullrow(i)!= -1){
        //System.out.println("row full: "+CheckFullrow(i));
        listRow.add(CheckFullrow(i)) ;
      }
    }
    for (int i=0; i<col; i++){
      if(CheckFullCol(i)!=-1){
        //System.out.println("col full: "+CheckFullCol(i));
        listCol.add(CheckFullCol(i));
      }
    }
    if(listRow.size() != 0){
      for (int i = 0; i < listRow.size();i++){
        desTroyRow(listRow.get(i));

      }
    }
    if (listCol.size() != 0){
      for (int i = 0; i < listCol.size();i++){
        desTroyCol(listCol.get(i));
      }
    }
  }
  private void desTroyRow(int row){
    for (int j = 0; j< col ;j ++){
      listAll[row][j] = 0;
      listAllBlock[row][j].addAction(sequence(delay(0.07f * j),alpha(0, 0.1f)));
    }
  }
  private void desTroyCol(int Col){
    for (int j = row-1; j >=0 ;j--){
      listAll[j][Col] = 0;
      listAllBlock[j][Col].addAction(sequence(delay(0.07f * j),alpha(0, 0.1f)));
    }
  }
  private int CheckFullrow(int row){
    int dem=0;
    for (int j=0;j<col;j++){
      if(listAll[row][j]==1){
        dem++;
      }
    }
    if(dem == col)
      return row;
    return -1;
  }
  private int CheckFullCol(int col){
    int dem=0;
    for (int j=0;j<row;j++){
      if(listAll[j][col]==1){
        dem++;
      }
    }
    if(dem==row)
      return col;
    return -1;
  }

}
