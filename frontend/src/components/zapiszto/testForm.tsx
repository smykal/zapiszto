import { Component } from "react";
import { Formik, Field, Form} from "formik";
import * as Yup from "yup";
import ZapiszToService from '../../services/zapiszto'
import AuthService from "../../services/auth.service";

type Props = {};

type State = {
  field_1: string,
  field_2: string,
  currentUserId: number
};

export default class TestForm extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.handlePost = this.handlePost.bind(this);


    
    this.state = {
      field_1: '',
      field_2: '',
      currentUserId: 99
    };
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();
    if (currentUser) {
      this.setState({
        currentUserId: currentUser.id,
      });
    }
  }

  componentWillUnmount() {
    window.location.reload();
  }

  validationSchema() {
    return Yup.object().shape({
      field_1: Yup.string().required("This field is required!"),
      field_2: Yup.string().required("This field is required!"),
    });
  }
  

  handlePost(formValue: { field_1: string; field_2: string }) {
    const { field_1, field_2 } = formValue;
    ZapiszToService.postTestData(
      field_1, 
      field_2, 
      this.state.currentUserId
    )
    
      window.location.reload();
  }

  render() {

    const initialValues = {
        field_1: this.state.field_1,
        field_2: this.state.field_2
    };

    return (
      <div className="col-md-12">
        <div className="card card-container">

          <Formik
            initialValues={initialValues}
            validationSchema={this.validationSchema}
            onSubmit={this.handlePost}
          >
            <Form>
              <div className="form-group">
                <Field as="select" name="field_1">
                  <option value="1">Chest</option>
                  <option value="2">Waist</option>
                  <option value="3">Hip</option>
                  <option value="4">Weight</option>
                </Field>
              </div>
              
              <div className="form-group">
                <label htmlFor="field_2">Kolumna 2</label>
                <Field name="field_2" type="text" className="form-control" />
              </div>

              <div className="form-group">
                <button type="submit" className="btn btn-primary btn-block">
                  <span>Wyślij</span>
                </button>
              </div>
            </Form>
          </Formik>
        </div>
      </div>
    );
  }
}
