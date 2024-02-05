import { Component } from "react";
import { Formik, Field, Form, ErrorMessage } from "formik";
import * as Yup from "yup";
import ZapiszToService from '../../services/zapiszto'
import AuthService from "../../services/auth.service";

type Props = {};

type State = {
  field_1: string,
  field_2: string,
  currentUserId: number;
  bodyParameters: { id: string; name: string }[];
};

export default class PutBodyParam extends Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.handlePost = this.handlePost.bind(this);

    this.state = {
      field_1: '',
      field_2: '',
      currentUserId: 99,
      bodyParameters: []
    };
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();
    if (currentUser) {
      this.setState({
        currentUserId: currentUser.id,
      });
    }
    ZapiszToService.getDictBodyParams().then(
      response => {
        this.setState({
          bodyParameters: response.data
        })
      }
    )
  }

  componentWillUnmount() {
    window.location.reload();
  }

  validationSchema() {
    return Yup.object().shape({
      field_1: Yup.number().required("This field is required!"),
      field_2: Yup.number().required("This field is required!"),
    });
  }


  handlePost(formValue: { field_1: string; field_2: string }) {
    const { field_1, field_2 } = formValue;
    ZapiszToService.postBodyParam(
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
      <div className="container">
        <Formik
          initialValues={initialValues}
          validationSchema={this.validationSchema}
          onSubmit={this.handlePost}
        >
          <Form>
            <div style={{ display: 'flex', marginBottom: '20px' }}>
              <div style={{ marginBottom: '10px', marginRight: '10px' }}>
                <Field as="select" name="field_1" className="form-control">
                  <option value="" disabled>
                    Select attribute
                  </option>
                  {this.state.bodyParameters.map((param) => (
                    <option key={param.id} value={param.id}>
                      {param.name}
                    </option>
                  ))}
                </Field>
                <ErrorMessage name="field_1" component="div" className="error" />
              </div>
              <div style={{ flex: 1, marginRight: '10px' }}>
                <Field name="field_2" type="text" className="form-control" />
                <ErrorMessage name="field_2" component="div" className="error" />
              </div>
              <div style={{ flex: 1 }}>
                <button type="submit" className="btn btn-primary btn-block">
                  <span>Add</span>
                </button>
              </div>
            </div>
          </Form>
        </Formik>
      </div>
    );
  }
}
