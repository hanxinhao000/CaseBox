# CaseBox

使用方式:


        /**
         *
         *
         * 切记CaseView使用方法
         *
         *
         * 可能项目后期会出现BUG,方便你好改
         *
         *
         * CaseView Adapter使用
         *
         * getCaseTtitle   //表示箱子的头显示字符,比如A008箱号
         *
         * getCaseCount    //表示在该区域有几个箱子
         *
         * getUse          //表示哪些被占用
         *
         * getCaseTtitle //
         *
         *
         *
         */
        caseView = findViewById(R.id.caseView);

        final String[] title = new String[]{"007号特大箱子", "008号迷你箱子", "009号中型箱子", "哈哈哈哈MMP箱子"};
        final String[] xinhao = new String[20];
        final String[] number = new String[20];
        for (int i = 0; i < 20; i++) {
            xinhao[i] = "规格:80CM*80CM";
        }
        final String[] money = new String[20];
        for (int i = 0; i < 20; i++) {
            money[i] = "¥:1.00";
        }
        for (int i = 0; i < 20; i++) {
            number[i] = "A000" + i;
        }
        XHCaseView xhCaseView = new XHCaseView(caseView);
        xhCaseView.setCaseViewAdapter(new XHCaseView.XHCaseAdapterBean() {


            /**
             * 获取你的箱子型号整个大箱子的型号
             * @param position
             * @return
             */
            @Override
            public String getCaseTtitle(int position) {
                return title[position];
            }

            /**
             * 获取你的箱子的小型号,每个格子的规格
             * @param position
             * @param index
             * @return
             */
            @Override
            public String getCaseModelNumber(int position, int index) {
                return xinhao[position];
            }

            /**
             * 每个箱子所使用的价格
             * @param position
             * @param index
             * @return
             */
            @Override
            public String getCaseMoney(int position, int index) {
                return money[position];
            }

            /**
             * 获取你每个箱子的箱号
             * @param position
             * @param index
             * @return
             */
            @Override
            public String getCaseNumber(int position, int index) {
                return number[position];
            }

            /**
             * 总共的箱子(大箱子)的个数
             * @return
             */
            @Override
            public int getCaseCount() {
                return 4;
            }

            /**
             * 将使那些箱子不能被选中,不可选
             * @param position
             * @param pageIndex
             * @return
             */
            @Override
            public boolean getUse(int position, int pageIndex) {
                return false;
            }
        });

        xhCaseView.setIsCheckData(this);
        
        
        
        XML:
        <com.jiuhong.redcabinet.red_view.CaseView
                    android:id="@+id/caseView"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"></com.jiuhong.redcabinet.red_view.CaseView>
        
        
