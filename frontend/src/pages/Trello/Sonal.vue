<template>
  <div v-if="init()" class="sonar-content">
    <div class="sonal-item">
      <span class="sonal-span">{{sonar.bugs}}</span>
      <div class="sonal-item_left">
        <div :class="'sonar-state '+this.bugs">{{this.bugs}}</div>
      </div>
      <div><span class="sonal-text">Bugs</span></div>
    </div>
    <div class="sonal-item">
      <span class="sonal-span">{{sonar.vulnerabilities}}</span>
      <div class="sonal-item_left">
        <div :class="'sonar-state '+this.vulnerabilities">{{this.vulnerabilities}}</div>
      </div>
      <div><span class="sonal-text">Vulnerabilities</span></div>
    </div>
    <div class="sonal-item">
      <span class="sonal-span">{{sonar.code_smells}}</span>
      <div class="sonal-item_left">
        <div :class="'sonar-state '+this.code_smells">{{this.code_smells}}</div>
      </div>
      <div><span class="sonal-text">Code Smells</span></div>
    </div>
    <div class="sonal-item">
      <span class="sonal-span">{{sonar.coverage}}%</span>
      <div class="circle-progress">
        <i-circle
          :percent="sonar.coverage"
          stroke-linecap="square"
          :size="20"
          :stroke-width="14"
          :trail-width="14"
          stroke-color="#00aa02"
          trail-color="#d4333f"
        />
      </div>
      <div><span class="sonal-text">Coverage</span></div>
    </div>
    <div class="sonal-item">
      <div>
        <span class="sonal-span">{{sonar.duplicated_lines_density}}%</span>
        <div class="sonal-item_left">
          <div :class="'Duplications-out-'+this.duplicated_lines_density">
            <div :class="'Duplications-in-'+this.duplicated_lines_density"></div>
          </div>
        </div>

      </div>
      <div><span class="sonal-text">Duplications</span></div>
    </div>
  </div>

</template>
<script>
export default {
  props: {
    sonar: {
      type: Object
    }
  },
  name: 'sonal',
  data() {
    return {
      bugs: '',
      vulnerabilities: '',
      code_smells: '',
      duplicated_lines_density: ''
    }
  },
  methods: {
    // 用init还是computed属性？
    init() {
      switch (parseInt(this.sonar.reliability_rating)) {
        case 1:
          this.bugs = 'A'
          break
        case 2:
          this.bugs = 'B'
          break
        case 3:
          this.bugs = 'C'
          break
        case 4:
          this.bugs = 'D'
          break
        case 5:
          this.bugs = 'E'
          break
      }
      switch (parseInt(this.sonar.security_rating)) {
        case 1:
          this.vulnerabilities = 'A'
          break
        case 2:
          this.vulnerabilities = 'B'
          break
        case 3:
          this.vulnerabilities = 'C'
          break
        case 4:
          this.vulnerabilities = 'D'
          break
        case 5:
          this.vulnerabilities = 'E'
          break
      }
      switch (parseInt(this.sonar.sqale_rating)) {
        case 1:
          this.code_smells = 'A'
          break
        case 2:
          this.code_smells = 'B'
          break
        case 3:
          this.code_smells = 'C'
          break
        case 4:
          this.code_smells = 'D'
          break
        case 5:
          this.code_smells = 'E'
          break
      }
      if (this.sonar.duplicated_lines_density > 20.0) {
        this.duplicated_lines_density = 'E'
      } else if (this.sonar.duplicated_lines_density > 10.0 && this.sonar.duplicated_lines_density <= 20.0) {
        this.duplicated_lines_density = 'D'
      } else if (this.sonar.duplicated_lines_density > 5.0 && this.sonar.duplicated_lines_density <= 10.0) {
        this.duplicated_lines_density = 'C'
      } else if (this.sonar.duplicated_lines_density > 0 && this.sonar.duplicated_lines_density <= 5.0) {
        this.duplicated_lines_density = 'B'
      } else if (this.sonar.duplicated_lines_density === 0.0) {
        this.duplicated_lines_density = 'A'
      }
      if (JSON.stringify(this.sonar) == '{}') {
        return false
      } else {
        return true
      }
    }
  }
}
</script>

<style scoped lang="scss">
  span{
    color: #495060;
  }
  .sonar-content {
    /*min-width: 360px;*/
  }

  .sonal-span {
    font-size: 12px;
    font-weight: bold;
    display: inline-block;
    padding-right: 6px;
  }

  .circle-progress {
    display: inline-block;
    position: relative;
    top: 5px;
    /*background-color: red;*/
  }

  .sonal-text {
    font-size: 11px;
    text-align: center;
    display: block;
  }

  .sonal-item {
    /*background-color: yellow;*/
    /*margin: 5px;*/
    width: 70px;
    display: inline-block;
    text-align: center;
    &_left {
      display: inline-block;
    }
    .sonar-state {
      width: 20px;
      height: 20px;
      border-radius: 15px;
      color: white;
      font-weight: bold;
      text-align: center;
      line-height: 20px;
      font-size: 14px;
    }
    .A {
      background-color: #00aa02;
    }
    .B {
      background-color: #b0d513;
    }
    .C {
      background-color: #e9bd06;
    }
    .D {
      background-color: #ed7c21;
    }
    .E {
      background-color: #ee0001;
    }
  }
  .Duplications-in-E {
    width: 12px;
    height: 12px;
    background-color: #ee0001;
    border-radius: 50%;
  }

  .Duplications-in-D {
    width: 10px;
    height: 10px;
    background-color: #ed7c21;
    border-radius: 50%;
  }

  .Duplications-in-C {
    width: 8px;
    height: 8px;
    background-color: #e9bd06;
    border-radius: 50%;
  }

  .Duplications-in-B {
    width: 6px;
    height: 6px;
    background-color: #b0d513;
    border-radius: 50%;
  }

  .Duplications-in-A {
    width: 4px;
    height: 4px;
    background-color: #fff;
    border-radius: 50%;
  }

  .Duplications-out-A {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    border: 3px solid #00aa02;
  }

  .Duplications-out-B {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    border: 3px solid #b0d513;
  }

  .Duplications-out-C {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    border: 3px solid #e9bd06;
  }

  .Duplications-out-D {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    border: 3px solid #ed7c21;
  }

  .Duplications-out-E {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    border: 3px solid #ee0001;
  }
</style>
